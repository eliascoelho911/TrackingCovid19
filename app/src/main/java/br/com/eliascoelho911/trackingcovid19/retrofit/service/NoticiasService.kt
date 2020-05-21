package br.com.eliascoelho911.trackingcovid19.retrofit.service

import br.com.eliascoelho911.trackingcovid19.model.GNewsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticiasService {
    @GET("v3/search")
    fun buscaNoticias(
        @Query("token") token: String,
        @Query("q") pesquisa: String,
        @Query("lang") idioma: String,
        @Query("image") imagemObrigatoria: Boolean
    ): Call<GNewsModel>
}