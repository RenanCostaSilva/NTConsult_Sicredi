package br.com.renancsdev.sicredi.api.chamada.validacao.body

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.renancsdev.sicredi.model.Feira
import retrofit2.Response

class RespostaRespostaBody(var context: Context) {

    fun verificarRespostaFeira(response: Response<Feira>): Boolean{

        var check = false
        if(verificarSerespostaTeveSucesso(response) && verificarBody(response))
           check = true

       return check
    }

    fun verificarBody(response: Response<Feira>): Boolean {

        var check = false
        if (response.body() != null) {
           check = true
        }
        else {
            Log.e("apiCallBackResposta", "Failed to get response")
            Toast.makeText(context , "Failed to get response" , Toast.LENGTH_SHORT).show()
        }
        return check
    }
    fun verificarSerespostaTeveSucesso(response: Response<Feira>): Boolean {

        var check = false
        if(response.isSuccessful) {
            check = true
        }else{
            Log.e("App", "${response.errorBody().toString()}")
            Toast.makeText(context , "${response.errorBody().toString()}" , Toast.LENGTH_SHORT).show()
        }
        return check
    }
}