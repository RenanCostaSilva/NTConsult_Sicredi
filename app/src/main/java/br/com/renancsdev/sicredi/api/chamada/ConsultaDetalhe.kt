package br.com.renancsdev.sicredi.api.chamada

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.api.ServiceBuilder
import br.com.renancsdev.sicredi.api.chamada.checkin.FazerCheckIn
import br.com.renancsdev.sicredi.api.chamada.validacao.body.RespostaRespostaBody
import br.com.renancsdev.sicredi.databinding.ActivityDetalheFeiraBinding
import br.com.renancsdev.sicredi.databinding.ActivityFeiraCheckInBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.ui.activity.FeiraCheckIn
import br.com.renancsdev.sicredi.viewmodel.extension.moeda
import br.com.renancsdev.sicredi.viewmodel.extension.mostarTexto
import br.com.renancsdev.sicredi.viewmodel.util.coordenada.Coordenada
import br.com.renancsdev.sicredi.viewmodel.util.data.Data
import br.com.renancsdev.sicredi.viewmodel.util.glide.Glides
import br.com.renancsdev.sicredi.viewmodel.util.midia.Sociais
import br.com.renancsdev.sicredi.viewmodel.validacao.Formulario
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultaDetalhe {

    private val request = ServiceBuilder.buildService(API::class.java)
    lateinit var context: Context

    private fun pegarContext(main: ActivityDetalheFeiraBinding): Context {
        return main.root.context
    }

    fun consultaFeira(feiraBinding: ActivityDetalheFeiraBinding , idDeConsulta: Int){

        context = pegarContext(feiraBinding)
        val call = request.pegarEventoSelecionado(idDeConsulta)

        call.enqueue(object : Callback<Feira> {
            override fun onResponse(call: Call<Feira>, response: Response<Feira>) {
                if(response.code() == 200){
                    consultaFeiraResposta(feiraBinding , response)
                }else {
                    Log.e("apiCallBack", "Response null")
                    Toast.makeText(context , "Response null" , Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Feira>, t: Throwable) {
                Log.e("apiCallBack", "${t.message}")
                Toast.makeText(context , "Failed to get response" , Toast.LENGTH_SHORT).show()
            }
        })

    }
    fun consultaFeiraResposta(feiraBinding: ActivityDetalheFeiraBinding , response: Response<Feira>){

        consultaRespostaBusca(feiraBinding , response)

    }

    private fun consultaRespostaBusca(feiraBinding: ActivityDetalheFeiraBinding , response: Response<Feira>){

        feiraBinding.flSicrediDetalheFeiraLoading.mostar()
        if(RespostaRespostaBody(context).verificarRespostaFeira(response)){

            compartilhar(feiraBinding , response.body()!!)
            exibirDadosDoCheckIn(feiraBinding , response.body()!!)
            fazerCheckInNaFeira(feiraBinding , response.body()!!)
        }

        feiraBinding.flSicrediDetalheFeiraLoading.esconder()

    }

    fun fazerCheckInNaFeira(feiraBinding: ActivityDetalheFeiraBinding , feira: Feira){

        feiraBinding.btnSicrediFeiraItemDetalheCheckin.setOnClickListener {
            var intent = Intent(context , FeiraCheckIn::class.java)
            Log.e("apiCallBack", "idFeira : ${feira.id}")
            intent.putExtra("idCheckIn" , intent.getIntExtra("idFeira" , feira.id))
            context.startActivity(intent)
        }

    }
    fun compartilhar(feiraBinding: ActivityDetalheFeiraBinding , feira: Feira){
        feiraBinding.imvShareSicrediFeiraItemDetalhe.setOnClickListener {
            Sociais(context).compartilhar(feira)
        }
    }

    fun exibirImagem(imageDestino : ImageView, imagemDestino: String){
        Glides(context).exibirImagemTratada(imagemDestino , imageDestino)
    }
    fun exibirNomeFeira(textView: TextView, texto : String){
        textView.text = texto
    }
    fun exibirdescricaoFeira(textView: TextView, texto : String){
        textView.text = texto
    }
    fun exibirPrecoFeira(textView: TextView, texto : Double){
        textView.text = texto.moeda()
    }
    fun exibirLocalFeira(textView: TextView, latitude : Double, longitude : Double){
        textView.text = Coordenada(context).endereco(latitude , longitude)
    }

    fun exibirDadosDoCheckIn(feiraBinding: ActivityDetalheFeiraBinding, feira: Feira){

        exibirImagem(feiraBinding.imvBgSicrediFeiraItemDetalhe      , feira.image)
        exibirImagem(feiraBinding.imvBgSicrediFeiraItemDetalheThumb , feira.image)

        exibirNomeFeira(feiraBinding.tvSicrediFeiraItemDetalheNomeFeira   , feira.title)
        exibirdescricaoFeira(feiraBinding.tvSicrediFeiraItemDetalheDescricao , feira.description)

        exibirPrecoFeira(feiraBinding.tvSicrediFeiraItemDetalhePreco , feira.price)
        exibirLocalFeira(feiraBinding.tvSicrediFeiraItemDetalheLocal , feira.latitude , feira.longitude)
    }

}