package br.com.renancsdev.sicredi.api.chamada.checkin

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.api.ServiceBuilder
import br.com.renancsdev.sicredi.api.chamada.validacao.body.RespostaRespostaBody
import br.com.renancsdev.sicredi.api.chamada.validacao.call.CallConsultaCheckIn
import br.com.renancsdev.sicredi.databinding.ActivityFeiraCheckInBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.viewmodel.extension.moeda
import br.com.renancsdev.sicredi.viewmodel.util.coordenada.Coordenada
import br.com.renancsdev.sicredi.viewmodel.util.data.Data
import br.com.renancsdev.sicredi.viewmodel.util.glide.Glides
import br.com.renancsdev.sicredi.viewmodel.validacao.Formulario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultaCheckIn(var nome : EditText , var email : EditText) {

    private val request = ServiceBuilder.buildService(API::class.java)
    lateinit var context: Context

    private fun pegarContext(main: ActivityFeiraCheckInBinding): Context {
        return main.root.context
    }

    fun consultaBusca(feiraBinding: ActivityFeiraCheckInBinding , idDeConsulta: Int){

        context = pegarContext(feiraBinding)
        val call = request.pegarEventoSelecionado(idDeConsulta)

        call.enqueue(object : Callback<Feira> {
            override fun onResponse(call: Call<Feira>, response: Response<Feira>) {

                if(response.code() == 200){
                    consultaRespostaBusca(feiraBinding , idDeConsulta , response)
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

    private fun consultaRespostaBusca(feiraBinding: ActivityFeiraCheckInBinding, idDeConsulta: Int, response: Response<Feira>){

        feiraBinding.flSicrediFeiraCheckinLoading.mostar()
        if(RespostaRespostaBody(context).verificarRespostaFeira(response)){
           exibirDadosDoCheckIn(feiraBinding , response.body()!!)
           inscreverNaFeira(feiraBinding ,response.body()!! , idDeConsulta)
        }
        feiraBinding.flSicrediFeiraCheckinLoading.esconder()

    }



    fun inscreverNaFeira(feiraBinding: ActivityFeiraCheckInBinding , feira: Feira , idDoEvento: Int){
        feiraBinding.btnSicrediFeiraItemDetalheFinalizar.setOnClickListener {
            if(Formulario(context).vallidarFormulario(nome.text.toString() , email.text.toString())){
                FazerCheckIn().fazerCheckInCheckin( feiraBinding , feira , idDoEvento ,  nome , email )
            }
        }
    }

    fun exibirImagem(feiraBinding: ActivityFeiraCheckInBinding , imagemDestino: String){
        Glides(context).exibirImagemTratada(imagemDestino , feiraBinding.imvSicrediFeiraItemEscolhidoBg)
    }
    fun exibirNomeFeira(textView: TextView , texto : String){
        textView.text = texto
    }
    fun exibirPrecoFeira(textView: TextView , texto : Double){
        textView.text = texto.moeda()
    }
    fun exibirLocalFeira(textView: TextView , latitude : Double , longitude : Double){
        textView.text = Coordenada(context).endereco(latitude , longitude)
    }
    fun exibirDataDaFeira(textView: TextView , data : Long ){
        textView.text = "${Data().ptbr(data)}"
    }

    fun exibirDadosDoCheckIn(feiraBinding: ActivityFeiraCheckInBinding , feira: Feira){
        exibirImagem(feiraBinding , feira.image)
        exibirNomeFeira(feiraBinding.tvSicrediFeiraItemEscolhidoNome   , feira.title)
        exibirPrecoFeira(feiraBinding.tvSicrediFeiraItemEscolhidoPreco , feira.price)
        exibirLocalFeira(feiraBinding.tvSicrediFeiraItemEscolhidoLocal , feira.latitude , feira.longitude)
        exibirDataDaFeira(feiraBinding.tvSicrediFeiraItemEscolhidoData , feira.date)
    }

}