package br.com.renancsdev.sicredi.ui.activity.apresentacao

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import br.com.renancsdev.sicredi.R
import br.com.renancsdev.sicredi.databinding.ActivityApresentacaoBinding
import br.com.renancsdev.sicredi.ui.activity.MainActivity
import br.dev.com.validacao.view.ui.animacao.Animacao
import java.util.*
import kotlin.concurrent.timerTask

class Apresentacao : AppCompatActivity() {

    private lateinit var apresentacaoBinding: ActivityApresentacaoBinding
    private var context: Context = this@Apresentacao
    private var activity: Activity = context as Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inicializarLayout()
        inicializaAnimacao()

    }

    //  Layout
    fun inicializarLayout(){
        inicializarMainLayout()
        inicializarDataBindingMainActivityLayout()
    }
    fun inicializarMainLayout(){
        setContentView(R.layout.activity_apresentacao)
    }
    fun inicializarDataBindingMainActivityLayout(){
        apresentacaoBinding = DataBindingUtil.setContentView(activity , R.layout.activity_apresentacao)
    }

    // Animação
    private fun inicializaAnimacao(){
        animacaoRedirecionar(apresentacaoBinding.clSicrediApresentacao , context)
    }
    private fun animacaoRedirecionar(constraint: ConstraintLayout, context: Context){
        Animacao().slideCimaBaixo(constraint , 2000)
        delayNaApresentacao(context)
    }
    fun delayNaApresentacao(context: Context){
        Timer().schedule(timerTask {
            var intent = Intent(context , MainActivity::class.java)
            startActivity(intent)
        }, 2000)
    }

}