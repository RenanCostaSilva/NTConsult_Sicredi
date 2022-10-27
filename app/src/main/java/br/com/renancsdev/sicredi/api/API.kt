package br.com.renancsdev.sicredi.api

import br.com.renancsdev.sicredi.model.Feira
import br.com.renancsdev.sicredi.model.Participante
import retrofit2.Call
import retrofit2.http.*

interface API {

    @GET("api/events")
    fun pegarEventos(): Call<List<Feira>>

    @GET("api/events/{id}")
    fun pegarEventoSelecionado(@Path(value = "id", encoded = false) id: Int):  Call<Feira>

    @POST("api/checkin/")
    fun efetuarCheckIn(@Body participante: Participante): Call<Participante>

}