package br.com.renancsdev.sicredi.viewmodel.util.glide

import android.content.Context
import android.widget.ImageView
import br.com.renancsdev.sicredi.R
import com.bumptech.glide.Glide.with
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class Glides(var context: Context) {

    fun exibirImagem(imagemPath: String , imagemViewDestino: ImageView){
        Glide.with(context)
          .load("$imagemPath")
          .apply(RequestOptions.bitmapTransform(RoundedCorners(10) ))
          .into(imagemViewDestino)
    }

    fun exibirImagemTratada(imagemPath: String , imagemViewDestino: ImageView){
        Glide.with(context)
            .load("$imagemPath")
            .placeholder(R.drawable.logo)
            .override(480,360)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10) ))
            .into(imagemViewDestino)
    }

}