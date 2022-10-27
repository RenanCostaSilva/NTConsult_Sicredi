package br.com.renancsdev.sicredi.viewmodel.extension

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

    fun Double.moeda(): String {
        var dec = DecimalFormat(",##")
        return "R$ ${dec.format(this)},00"
    }
