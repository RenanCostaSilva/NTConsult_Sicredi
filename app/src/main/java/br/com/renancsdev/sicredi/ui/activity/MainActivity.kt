package br.com.renancsdev.sicredi.ui.activity

import android.R.layout
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renancsdev.sicredi.R
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.api.ServiceBuilder
import br.com.renancsdev.sicredi.api.chamada.ConsultaFeira
import br.com.renancsdev.sicredi.databinding.ActivityMainBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.viewmodel.adapter.FeiraAdapter
import br.com.renancsdev.sicredi.viewmodel.util.conexao.Wifi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    //https://github.com/WoopSicredi/jobs/issues/1

    /*A API de eventos está disponivel em:
    https://5f5a8f24d44d640016169133.mockapi.io/api/events*/

    /*Para acessar cada detalhe do evento basta adicionar o ID do item ao final da URL. Ex:
    https://5f5a8f24d44d640016169133.mockapi.io/api/events/1*/
    
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inicializarTela()
        verificaSeCarregaFeiras()

    }

    fun verificaSeCarregaFeiras(){
        Log.e("App" , "WIFI: ${seHaInternet()}")
        if(seHaInternet()){
            fazerConsulta()
        }else{
            Toast.makeText(this@MainActivity , "Sem conexão , saindo ...." , Toast.LENGTH_SHORT).show()
            this@MainActivity.finish()
            exitProcess(0)
        }
    }
    fun seHaInternet(): Boolean {
       return Wifi(mainBinding.root.context).verificSeHaInternet()
    }

    fun inicializarTela(){
        setarLayout()
        setarBinding()
        esconderEventos(mainBinding)
    }
    fun setarLayout(){
        setContentView(R.layout.activity_main)
    }
    fun setarBinding() {
        mainBinding = DataBindingUtil.setContentView(this , R.layout.activity_main)
    }
    fun esconderEventos(mainBinding: ActivityMainBinding){
        mainBinding.tvSicrediTituloEvento.esconder()
    }
    fun fazerConsulta(){
        ConsultaFeira().consulta(mainBinding)
    }

}