package br.com.renancsdev.sicredi.api.chamada.checkin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.display.DisplayManagerCompat
import br.com.renancsdev.sicredi.R
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.api.ServiceBuilder
import br.com.renancsdev.sicredi.databinding.ActivityFeiraCheckInBinding
import br.com.renancsdev.sicredi.databinding.DialogSucessoCheckinBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.model.Participante
import br.com.renancsdev.sicredi.ui.activity.MainActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FazerCheckIn{

    private val request = ServiceBuilder.buildService(API::class.java)
    lateinit var context: Context

    private fun pegarContext(main: ActivityFeiraCheckInBinding): Context {
        return main.root.context
    }

    // api checkin
   fun fazerCheckInCheckin(feiraBinding: ActivityFeiraCheckInBinding , feira: Feira , id: Int , nome: EditText , email: EditText){

        context = pegarContext(feiraBinding)
        var p = Participante(id , nome.text.toString() , email.text.toString())
        val call = request.efetuarCheckIn(p)

        call.enqueue(object : Callback<Participante> {
            override fun onResponse(call: Call<Participante>, response: Response<Participante>) {
                if(response.code() == 201){

                    fazerCheckInRespostaCheckin( feira , nome , email , response)

                }else {
                    Log.e("apiCallBack", "Response null")
                    Toast.makeText(context , "Response null" , Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Participante>, t: Throwable) {
                Log.e("apiCallBack", "${t.message}")
                Toast.makeText(context , "Failed to get response" , Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun fazerCheckInRespostaCheckin(feira: Feira , nome: EditText , email: EditText , response: Response<Participante>){

        if(response.isSuccessful) {

            if (response.body() != null) {
                if(response.body()!= null){

                    dialogMensagemSucesso(context , feira , nome.text.toString() , email.text.toString())
                    limparCampos(nome)
                    limparCampos(email)

                }else{
                    Log.e("apiCallBackResposta", "Erro: ${response.body()}")
                    Toast.makeText(context , "Erro ao popular a lista" , Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("apiCallBackResposta", "Failed to get response")
                Toast.makeText(context , "Failed to get response" , Toast.LENGTH_SHORT).show()
            }

        }else{
            Log.e("App", "${response.errorBody().toString()}")
            Toast.makeText(context , "${response.errorBody().toString()}" , Toast.LENGTH_SHORT).show()
        }

    }

    //limpar campos
    fun limparCampos(campo: EditText){
        campo.text   = null
    }

    //dialog
    private fun dialogMensagemSucesso(context: Context , feira: Feira , nome: String , email: String){

        val dialog: AlertDialog = setarLayoutDialog(context , R.style.CustomAlertDialog).create()
        val binding = inflarTelaDoDialogCustomizado(context)

        var nome = "Parabéns ${nome.uppercase()} !!.\n\n"
        var mensagem = "Você está inscrito no no evento: \n(${feira.title.uppercase()}).\n\nEm breve você receberá no seu email ( $email ) os dados do evento.\nVocê pode utilizar o QRCode acime para ver mais informações"

        binding.imvSicrediFeiraItemDialogSucess.setImageBitmap(gerarQRCode( feira.title , binding.imvSicrediFeiraItemDialogSucess))
        binding.tvSicrediFeiraItemDialogSucessNome.text = nome
        binding.tvSicrediFeiraItemDialogSucessMsg.text  = mensagem
        binding.btnSicrediFeiraItemDialogSucessFechar.setOnClickListener {
            dialog.dismiss()
            context.startActivity(Intent(context , MainActivity::class.java))
        }

        alterarAlturaLarguraCaixaDialog(dialog)
        dialog.setView(binding.root)
        dialog.show()

    }
    fun alterarAlturaLarguraCaixaDialog(dialog: AlertDialog): AlertDialog {

        var displayWidth  : Int
        var displayHeight : Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val defaultDisplay = DisplayManagerCompat.getInstance(context).getDisplay(Display.DEFAULT_DISPLAY)
            val displayContext = context.createDisplayContext(defaultDisplay!!)

            displayWidth  = displayContext.resources.displayMetrics.widthPixels
            displayHeight = displayContext.resources.displayMetrics.heightPixels

        } else {

            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

            displayWidth = displayMetrics.heightPixels
            displayHeight = displayMetrics.widthPixels

        }

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)

        val dialogWindowWidth  = (displayWidth  * 0.8f).toInt()
        val dialogWindowHeight = (displayHeight * 0.45f).toInt()

        layoutParams.width = dialogWindowWidth
        layoutParams.height = dialogWindowHeight
        dialog.window!!.attributes = layoutParams

        return dialog

    }
    fun setarLayoutDialog(context: Context , estilo: Int): AlertDialog.Builder {
        return AlertDialog.Builder(context , estilo)
    }
    fun inflarTelaDoDialogCustomizado(context: Context): DialogSucessoCheckinBinding {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return DialogSucessoCheckinBinding.inflate(inflater)
    }

    //Qrcode
    private fun gerarQRCode(dados: String , imagem: ImageView): Bitmap {
        //Toast.makeText(this , "Gerar" , Toast.LENGTH_SHORT).show()

        val size = imagem.drawable.intrinsicWidth
        //val sizeH = binding.qrcodeImagem.drawable.intrinsicHeight
        //val size = 512 //pixels

        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(dados, BarcodeFormat.QR_CODE, size, size)
        val qrcode = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
        return  qrcode
    }

}