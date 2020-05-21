package br.com.eliascoelho911.trackingcovid19.repository

import br.com.eliascoelho911.trackingcovid19.model.Pais
import br.com.eliascoelho911.trackingcovid19.model.Report.Dados
import br.com.eliascoelho911.trackingcovid19.retrofit.ReportDadosRetrofit
import br.com.eliascoelho911.trackingcovid19.retrofit.callback.Callback

class ReportDadosRepository {
    private val service = ReportDadosRetrofit().service
    private var dadosDeTodosOsPaises = listOf<Dados>()
    var dadosDeTodosOsEstadosBrasileiros = listOf<Dados>()

    fun buscaDadosDeTodosOsPaises(
        quandoSucesso: (resultado: List<Dados>) -> Unit,
        quandoFalha: (erro: String) -> Unit
    ) {
        service.buscaDadosDeTodosOsPaises().enqueue(
            Callback(
                {
                    dadosDeTodosOsPaises = it.data
                    quandoSucesso(it.data)
                }, {
                    quandoFalha(it)
                }
            )
        )
    }

    fun buscaDadosTodosEstadosBrasileiros(
        quandoSucesso: (resultado: List<Dados>) -> Unit,
        quandoFalha: (erro: String) -> Unit
    ) {
        service.buscaDadosDeTodosOsEstados().enqueue(
            Callback(
                {
                    dadosDeTodosOsEstadosBrasileiros = it.data
                    quandoSucesso(it.data)
                }, {
                    quandoFalha(it)
                }
            ))
    }

    fun buscaPorPais(pais: Pais): Dados {
        if (pais.name == "Mundo") {
            val dadosDoMundo = Dados("Mundo", 0, 0, 0, "")
            dadosDeTodosOsPaises.forEach {
                dadosDoMundo.casosConfirmados = dadosDoMundo.casosConfirmados.plus(it.casosConfirmados)
                dadosDoMundo.mortes = dadosDoMundo.mortes.plus(it.mortes)
                dadosDoMundo.recuperados = dadosDoMundo.recuperados.plus(it.recuperados)
            }
            return dadosDoMundo
        } else {
            var resultadoDaBusca = dadosDeTodosOsPaises.find {
                pais.name == it.nome
            }

            if (resultadoDaBusca == null) {
                resultadoDaBusca = Dados(pais.name, 0, 0, 0, "")
            }
            return resultadoDaBusca
        }
    }
}