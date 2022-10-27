package br.com.renancsdev.sicredi.ui.activity

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.renancsdev.sicredi.R
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.api.ServiceBuilder
import br.com.renancsdev.sicredi.api.chamada.ConsultaDetalhe
import br.com.renancsdev.sicredi.databinding.ActivityDetalheFeiraBinding
import br.com.renancsdev.sicredi.databinding.ActivityMainBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.DecimalFormat
import java.util.*

class DetalheFeira : AppCompatActivity() {

    private lateinit var detalheFeiraBinding: ActivityDetalheFeiraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inicializarTela()
        consultaDetalheApi()

    }

    fun inicializarTela(){
        setarLayout()
        setarBinding()
    }
    fun setarLayout(){
        setContentView(R.layout.activity_detalhe_feira)
    }
    fun setarBinding() {
        detalheFeiraBinding = DataBindingUtil.setContentView(this , R.layout.activity_detalhe_feira)
    }
    fun consultaDetalheApi(){
        ConsultaDetalhe().consultaFeira(detalheFeiraBinding , intent.getIntExtra("idFeira" , 0))
    }

}