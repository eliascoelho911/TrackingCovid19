package br.com.eliascoelho911.trackingcovid19.repository

import br.com.eliascoelho911.trackingcovid19.model.Pais
import br.com.eliascoelho911.trackingcovid19.retrofit.PaisRetrofit
import br.com.eliascoelho911.trackingcovid19.retrofit.callback.Callback

class PaisRepository {
    private var listaDePaises = listOf<Pais>()
    fun buscaTodosOsPaises(
        quandoSucesso: (List<Pais>) -> Unit,
        quandoFalha: (erro: String) -> Unit
    ) {
        val service = PaisRetrofit().service
        service.buscaTodosOsPaises().enqueue(
            Callback({
                listaDePaises = it
                quandoSucesso(it)
            },
                {
                    quandoFalha(it)
                })
        )
    }
}