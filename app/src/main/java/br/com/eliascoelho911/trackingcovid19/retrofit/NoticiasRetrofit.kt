package br.com.eliascoelho911.trackingcovid19.retrofit

import br.com.eliascoelho911.trackingcovid19.retrofit.service.NoticiasService

class NoticiasRetrofit : RetrofitBase<NoticiasService>(
    baseUrl = "https://gnews.io/api/",
    serviceClass = NoticiasService::class.java
)