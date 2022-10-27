package br.com.renancsdev.sicredi.viewmodel.holder

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.renancsdev.sicredi.R
import br.com.renancsdev.sicredi.databinding.FeiraItemBinding
import br.com.renancsdev.sicredi.extension.esconder
import br.com.renancsdev.sicredi.extension.mostar
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.ui.activity.DetalheFeira
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class FeiraViewHolder(private var binding: FeiraItemBinding): RecyclerView.ViewHolder(binding.root) {

    private var context: Context = binding.root.context

    fun bind(feira: Feira){
        iniciarServicos(context , feira)
    }

    fun iniciarServicos(context: Context, feira: Feira){


        binding.flSicrediFeiraItemLoading.mostar()
        Glide.with(context)
            .load("${feira.image}")
            .placeholder(R.drawable.logo)
            .override(480,360)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?,model: Any?,target: Target<Drawable>?,isFirstResource: Boolean): Boolean {
                    binding.flSicrediFeiraItemLoading.esconder()
                    return false
                }
                override fun onResourceReady(resource: Drawable?,model: Any?,target: Target<Drawable>?,dataSource: DataSource?,isFirstResource: Boolean): Boolean {
                    binding.flSicrediFeiraItemLoading.esconder()
                    return false
                }
            })
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10) ))
            .error(R.drawable.image404)
            .into(binding.imvSicrediFeiraItem)


        binding.tvSicrediFeiraItemDescricao.text    = feira.description
        binding.tvSicrediFeiraItemTitulo.text       = feira.title
        binding.tvSicrediFeiraItemPreco.text        = "${doubleToMoeda(feira.price)},00"
        binding.tvSicrediFeiraItemData.text         = "${dataApiToPTBR(feira.date)}"

        binding.cardviewSicrediFeiraItem.setOnClickListener {
            var intent = Intent(context , DetalheFeira::class.java)
            intent.putExtra("idFeira" , feira.id)
            context.startActivity(intent)
        }


    }

    private fun dataApiToPTBR(data: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(data)
        return sdf.format(netDate)
    }

    fun doubleToMoeda(valor: Double): String {
        var dec = DecimalFormat(",##")
        return "R$ ${dec.format(valor)}"
    }

}