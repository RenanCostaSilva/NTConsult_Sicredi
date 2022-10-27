package br.com.renancsdev.sicredi.viewmodel.util.midia

import android.content.Context
import android.content.Intent
import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.viewmodel.util.coordenada.Coordenada

class Sociais(var context: Context) {

    fun compartilhar(feira: Feira){
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_TEXT, "${feira.title} - ${Coordenada(context).endereco(feira.latitude , feira.longitude)}")
        context.startActivity(Intent.createChooser(share,""))
    }

}