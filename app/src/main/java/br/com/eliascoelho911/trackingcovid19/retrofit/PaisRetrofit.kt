package br.com.eliascoelho911.trackingcovid19.retrofit

import br.com.eliascoelho911.trackingcovid19.retrofit.service.PaisService

class PaisRetrofit : RetrofitBase<PaisService>(
    baseUrl = "https://restcountries.eu/rest/v2/",
    serviceClass = PaisService::class.java
)