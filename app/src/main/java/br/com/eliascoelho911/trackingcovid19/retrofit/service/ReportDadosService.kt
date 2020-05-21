package br.com.eliascoelho911.trackingcovid19.retrofit.service

import br.com.eliascoelho911.trackingcovid19.model.Report
import retrofit2.Call
import retrofit2.http.GET

interface ReportDadosService {
    @GET("report/v1/countries")
    fun buscaDadosDeTodosOsPaises(): Call<Report>

    @GET("report/v1")
    fun buscaDadosDeTodosOsEstados(): Call<Report>
}