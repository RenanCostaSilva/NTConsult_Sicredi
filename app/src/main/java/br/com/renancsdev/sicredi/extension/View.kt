package br.com.renancsdev.sicredi.extension

import android.view.View

fun View.mostar(){
    this.visibility = View.VISIBLE
}

fun View.esconder(){
    this.visibility = View.GONE
}