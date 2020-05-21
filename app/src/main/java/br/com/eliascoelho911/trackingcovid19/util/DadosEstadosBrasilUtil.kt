package br.com.eliascoelho911.trackingcovid19.util

import br.com.eliascoelho911.trackingcovid19.repository.ReportDadosRepository
import br.com.eliascoelho911.trackingcovid19.ui.adapter.EstadosBrasilAdapter

class DadosEstadosBrasilUtil(private val reportDadosRepository: ReportDadosRepository) {
    fun getTabelaDeMortes() : List<EstadosBrasilAdapter.Linha> {
        val list = mutableListOf<EstadosBrasilAdapter.Linha>()
        reportDadosRepository.dadosDeTodosOsEstadosBrasileiros.forEach {
            list.add(EstadosBrasilAdapter.Linha(it.nome, it.mortes))
        }

        return list
    }

    fun getTabelaDeCasos() : List<EstadosBrasilAdapter.Linha> {
        val list = mutableListOf<EstadosBrasilAdapter.Linha>()
        reportDadosRepository.dadosDeTodosOsEstadosBrasileiros.forEach {
            list.add(EstadosBrasilAdapter.Linha(it.nome, it.casosConfirmados))
        }

        return list
    }

    fun getTabelaDeRecuperados(): List<EstadosBrasilAdapter.Linha> {
        val list = mutableListOf<EstadosBrasilAdapter.Linha>()
        reportDadosRepository.dadosDeTodosOsEstadosBrasileiros.forEach {
            list.add(EstadosBrasilAdapter.Linha(it.nome, it.recuperados))
        }

        return list
    }
}