package br.com.renancsdev.sicredi.model

import java.util.*

data class Feira(

    val date: Long,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Double,
    val title: String,
    val id: Int
)
