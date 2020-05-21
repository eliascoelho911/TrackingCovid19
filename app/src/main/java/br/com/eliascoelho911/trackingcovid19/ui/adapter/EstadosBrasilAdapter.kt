package br.com.eliascoelho911.trackingcovid19.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.eliascoelho911.trackingcovid19.R
import br.com.eliascoelho911.trackingcovid19.model.Ordem
import br.com.eliascoelho911.trackingcovid19.model.Ordem.CRESCENTE
import br.com.eliascoelho911.trackingcovid19.model.Ordem.DECRESCENTE
import br.com.eliascoelho911.trackingcovid19.ui.adapter.EstadosBrasilAdapter.Coluna.ESTADO
import br.com.eliascoelho911.trackingcovid19.ui.adapter.EstadosBrasilAdapter.Coluna.VALOR
import br.com.eliascoelho911.trackingcovid19.ui.adapter.EstadosBrasilAdapter.EstadoViewHolder
import kotlinx.android.synthetic.main.item_dashboard_estados_brasil.view.*

open class EstadosBrasilAdapter(
    private var dados: List<Linha>,
    private val context: Context
) :
    Adapter<EstadoViewHolder>() {

    var listaExpandida = false
        private set

    private val ordemDaTabela = OrdemDaTabela()

    private fun pegaDadosAdaptados(): List<Linha> {
        return if (listaExpandida) {
            dados
        } else {
            dados.subList(0, 5)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadoViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_dashboard_estados_brasil, parent, false)
        return EstadoViewHolder(view)
    }

    fun exibeSoOs5Primeiros() {
        listaExpandida = false
        atualiza()
    }

    fun exibeTodos() {
        listaExpandida = true
        atualiza()
    }

    private fun atualiza() {
        notifyDataSetChanged()
    }

    fun atualiza(novaLista: List<Linha>) {
        this.dados = novaLista
        atualiza()
        atualizaOrdem()
    }

    private fun atualizaOrdem() {
        if (ordemDaTabela.ordenandoPelaColuna != null) {
            if (ordemDaTabela.ordem != null) {
                ordenaPor(ordemDaTabela.ordenandoPelaColuna!!, ordemDaTabela.ordem!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return pegaDadosAdaptados().size
    }

    override fun onBindViewHolder(holder: EstadoViewHolder, position: Int) {
        holder.vincula(pegaDadosAdaptados()[position])
    }

    private fun ordenaPorEstado(ordem: Ordem) {
        dados = when (ordem) {
            CRESCENTE -> {
                dados.sortedBy { d ->
                    d.estado
                }
            }

            DECRESCENTE -> {
                dados.sortedByDescending { d ->
                    d.estado
                }
            }
        }
        atualiza()
        ordemDaTabela.ordenandoPelaColuna = ESTADO
        ordemDaTabela.ordem = ordem
    }

    private fun ordenaPorValor(ordem: Ordem) {
        dados = when (ordem) {
            CRESCENTE -> {
                dados.sortedBy { d ->
                    d.valor
                }
            }

            DECRESCENTE -> {
                dados.sortedByDescending { d ->
                    d.valor
                }
            }
        }
        atualiza()
        ordemDaTabela.ordenandoPelaColuna = VALOR
        ordemDaTabela.ordem = ordem
    }

    fun ordenaPor(coluna: Coluna, ordem: Ordem) {
        if (coluna == ESTADO) {
            ordenaPorEstado(ordem)
        } else if (coluna == VALOR) {
            ordenaPorValor(ordem)
        }
    }

    fun ordenaPor(coluna: Coluna): Ordem {
        val ordem = encontraNovaOrdem(coluna)

        ordenaPor(coluna, ordem)

        return ordem
    }

    private fun encontraNovaOrdem(coluna: Coluna): Ordem {
        return if (ordenandoPelaColuna(coluna)) {
            inverteOrdem()
        } else {
            DECRESCENTE
        }
    }

    private fun inverteOrdem(): Ordem {
        return if (ordemDaTabela.ordem == DECRESCENTE) {
            CRESCENTE
        } else {
            DECRESCENTE
        }
    }

    private fun ordenandoPelaColuna(coluna: Coluna): Boolean {
        return if (ordemDaTabela.ordenandoPelaColuna != null) {
            coluna == ordemDaTabela.ordenandoPelaColuna
        } else {
            false
        }
    }

    class EstadoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun vincula(dados: Linha) {
            view.item_dashboard_estados_brasil_numero.text =
                dados.valor.toString()
            view.item_dashboard_estados_brasil_estado.text = dados.estado
        }
    }

    enum class Coluna {
        ESTADO, VALOR
    }

    class OrdemDaTabela {
        var ordenandoPelaColuna: Coluna? = null
        var ordem: Ordem? = null
    }

    data class Linha(val estado: String, val valor: Long)
}