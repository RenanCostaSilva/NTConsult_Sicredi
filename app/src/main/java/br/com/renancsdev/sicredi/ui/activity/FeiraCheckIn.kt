package br.com.renancsdev.sicredi.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.databinding.DataBindingUtil
import br.com.renancsdev.sicredi.R
import br.com.renancsdev.sicredi.api.API
import br.com.renancsdev.sicredi.api.ServiceBuilder
import br.com.renancsdev.sicredi.api.chamada.ConsultaFeira
import br.com.renancsdev.sicredi.api.chamada.checkin.ConsultaCheckIn
import br.com.renancsdev.sicredi.api.chamada.checkin.FazerCheckIn
import br.com.renancsdev.sicredi.databinding.ActivityFeiraCheckInBinding
import br.com.renancsdev.sicredi.databinding.ActivityMainBinding
import br.com.renancsdev.sicredi.databinding.DialogSucessoCheckinBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.model.Participante
import br.com.renancsdev.sicredi.viewmodel.extension.moeda
import br.com.renancsdev.sicredi.viewmodel.util.coordenada.Coordenada
import br.com.renancsdev.sicredi.viewmodel.util.glide.Glides
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class FeiraCheckIn : AppCompatActivity() {

    private lateinit var feiraCheckInBinding: ActivityFeiraCheckInBinding
    private var context: Context = this@FeiraCheckIn
    private val request = ServiceBuilder.buildService(API::class.java)
    private var coroutineMain =  CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inicializarTela()
        fazerCheckIn()

    }


    fun inicializarTela(){
        setarLayout()
        setarBinding()
    }
    fun setarLayout(){
        setContentView(R.layout.activity_feira_check_in)
    }
    fun setarBinding() {
        feiraCheckInBinding = DataBindingUtil.setContentView(this , R.layout.activity_feira_check_in)
    }

    private fun fazerCheckIn(){
        coroutineMain.launch {

            feiraCheckInBinding.flSicrediFeiraCheckinLoading.mostar()
            ConsultaCheckIn(feiraCheckInBinding.editSicrediFeiraItemDetalheNome , feiraCheckInBinding.editSicrediFeiraItemDetalheEmail)
                .consultaBusca( feiraCheckInBinding , intent.getIntExtra("idCheckIn" , 0))
        }
    }

}