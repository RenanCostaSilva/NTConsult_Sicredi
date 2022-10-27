package br.com.renancsdev.sicredi.api.chamada.validacao.call

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.model.Feira
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallConsultaCheckIn(var context: Context , var request: API) {

    fun verificaCheckin(idDeConsulta: Int): Boolean {

        val call = request.pegarEventoSelecionado(idDeConsulta)
        var check = false

        call.enqueue(object : Callback<Feira> {
            override fun onResponse(call: Call<Feira>, response: Response<Feira>) {

                if(response.code() == 200){

                    check = true

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

        return check
    }

}