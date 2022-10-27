package br.com.renancsdev.sicredi.api.chamada

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.api.ServiceBuilder
import br.com.renancsdev.sicredi.databinding.ActivityMainBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.viewmodel.adapter.FeiraAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ConsultaFeira {

    private val request = ServiceBuilder.buildService(API::class.java)
    lateinit var context: Context

    fun pegarContext(main: ActivityMainBinding): Context {
        return main.root.context
    }

    fun consulta(mainBinding: ActivityMainBinding){

        context = pegarContext(mainBinding)

        mainBinding.tvSicrediTituloEvento.mostar()
        val call = request.pegarEventos()
        call.enqueue(object : Callback<List<Feira>> {
            override fun onResponse(call: Call<List<Feira>>, response: Response<List<Feira>>) {
                Log.e("apiCallBack", "$response")
                if(response.code() == 200){
                    consultaResposta( mainBinding , response)
                }else {
                    Log.e("apiCallBack", "Response null")
                    Toast.makeText(mainBinding.root.context , "Response null" , Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Feira>>, t: Throwable) {
                Log.e("apiCallBack", "${t.message}")
                Toast.makeText(context , "Failed to get response" , Toast.LENGTH_SHORT).show()
            }
        })

    }
    fun consultaResposta(mainBinding: ActivityMainBinding , response: Response<List<Feira>>){

        context = pegarContext(mainBinding)
        if(response.isSuccessful) {

            mainBinding.flSicrediEventoLoading.mostar()
            if (response.body() != null) {
                if(response.body()!= null){

                    AdapterFilmeaGridView(response.body()!!, mainBinding )
                    response.body()!!.forEach { x ->
                        Log.e("apiCallBackResposta", "ID: ${x.id} - Title: ${x.title}")
                    }

                }else{
                    Log.e("apiCallBackResposta", "Erro: ${response.body()}")
                    Toast.makeText(context , "Erro ao popular a lista" , Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("apiCallBackResposta", "Failed to get response")
                Toast.makeText(context , "Failed to get response" , Toast.LENGTH_SHORT).show()
            }
            mainBinding.flSicrediEventoLoading.esconder()

        }else{
            Log.e("App", "${response.errorBody().toString()}")
            Toast.makeText(context , "${response.errorBody().toString()}" , Toast.LENGTH_SHORT).show()
        }

    }

    //Adapter
    private fun AdapterFilmeaGridView(filmes: List<Feira>, binding: ActivityMainBinding): FeiraAdapter {

        context = pegarContext(binding)
        val adapter = FeiraAdapter(filmes)
        val layoutManager = LinearLayoutManager(context)
        binding.rvSicrediEvento.layoutManager = layoutManager
        binding.rvSicrediEvento.adapter = adapter
        return adapter
    }

}