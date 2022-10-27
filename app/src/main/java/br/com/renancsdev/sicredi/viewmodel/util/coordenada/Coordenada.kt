package br.com.renancsdev.sicredi.viewmodel.util.coordenada

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import java.io.IOException
import java.util.*

class Coordenada(var context: Context) {

    fun endereco(latitude: Double , longitude: Double): String {

        val addresses: List<Address>
        var local = ""

        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            addresses = geocoder.getFromLocation(latitude,longitude,1)
            local = addresses[0].getAddressLine(0)
        }catch (e: IOException){
            Toast.makeText(context , "Não foi possível carregar o local do evento:\n${e.message}" , Toast.LENGTH_SHORT).show()
            local = "Não foi possível carregar o local do evento"
        }
        return local
    }

}