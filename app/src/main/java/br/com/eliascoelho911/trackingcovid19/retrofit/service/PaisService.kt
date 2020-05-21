package br.com.eliascoelho911.trackingcovid19.retrofit.service

import br.com.eliascoelho911.trackingcovid19.model.Pais
import retrofit2.Call
import retrofit2.http.GET

interface PaisService {
    @GET("all")
    fun buscaTodosOsPaises(): Call<List<Pais>>
}