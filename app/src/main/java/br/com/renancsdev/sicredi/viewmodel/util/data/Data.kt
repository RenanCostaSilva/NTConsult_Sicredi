package br.com.renancsdev.sicredi.viewmodel.util.data

import java.text.SimpleDateFormat
import java.util.*

class Data {

    fun ptbr(data: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(data)
        return sdf.format(netDate)
    }

}