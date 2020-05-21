package br.com.eliascoelho911.trackingcovid19.retrofit

import br.com.eliascoelho911.trackingcovid19.retrofit.service.ReportDadosService

class ReportDadosRetrofit : RetrofitBase<ReportDadosService>(
    baseUrl = "https://covid19-brazil-api.now.sh/api/",
    serviceClass = ReportDadosService::class.java
)