package br.com.eliascoelho911.trackingcovid19.repository

import br.com.eliascoelho911.trackingcovid19.model.GNewsModel.Noticia
import br.com.eliascoelho911.trackingcovid19.retrofit.NoticiasRetrofit
import br.com.eliascoelho911.trackingcovid19.retrofit.callback.Callback

class NoticiasRepository {
    private val service = NoticiasRetrofit().service

    fun buscaNoticiasDeCovid19(
        quandoSucesso: (List<Noticia>) -> Unit,
        quandoFalha: (erro: String) -> Unit
    ) {
        service.buscaNoticias("28d73bbc6f57cca59533684a447cece2", "Covid-19", "pt-BR", true)
            .enqueue(Callback({
                quandoSucesso(it.noticias)
            }, {
                quandoFalha(it)
            }))
    }
}