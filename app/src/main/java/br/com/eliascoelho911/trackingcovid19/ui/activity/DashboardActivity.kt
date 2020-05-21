package br.com.eliascoelho911.trackingcovid19.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import br.com.eliascoelho911.trackingcovid19.R
import br.com.eliascoelho911.trackingcovid19.R.drawable.ic_seta_pra_baixo_dashboard
import br.com.eliascoelho911.trackingcovid19.R.drawable.ic_seta_pra_cima_dashboard
import br.com.eliascoelho911.trackingcovid19.model.Ordem
import br.com.eliascoelho911.trackingcovid19.model.Ordem.CRESCENTE
import br.com.eliascoelho911.trackingcovid19.model.Ordem.DECRESCENTE
import br.com.eliascoelho911.trackingcovid19.model.Pais
import br.com.eliascoelho911.trackingcovid19.repository.NoticiasRepository
import br.com.eliascoelho911.trackingcovid19.repository.PaisRepository
import br.com.eliascoelho911.trackingcovid19.repository.ReportDadosRepository
import br.com.eliascoelho911.trackingcovid19.ui.adapter.EstadosBrasilAdapter
import br.com.eliascoelho911.trackingcovid19.ui.adapter.EstadosBrasilAdapter.Coluna.ESTADO
import br.com.eliascoelho911.trackingcovid19.ui.adapter.EstadosBrasilAdapter.Coluna.VALOR
import br.com.eliascoelho911.trackingcovid19.ui.adapter.NoticiasAdapter
import br.com.eliascoelho911.trackingcovid19.ui.adapter.SpinnerPaisesAdapter
import br.com.eliascoelho911.trackingcovid19.util.CompoundDrawablesUtil.Companion.insereNo
import br.com.eliascoelho911.trackingcovid19.util.CompoundDrawablesUtil.Companion.limpaDrawables
import br.com.eliascoelho911.trackingcovid19.util.CompoundDrawablesUtil.Lado.DIREITA
import br.com.eliascoelho911.trackingcovid19.util.DadosEstadosBrasilUtil
import br.com.eliascoelho911.trackingcovid19.util.ExibidorDeErros.Companion.erroAoTentarConectarComServidor
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {

    private lateinit var spinnerPaisesAdapter: SpinnerPaisesAdapter
    private val estadosBrasilAdapter by lazy {
        EstadosBrasilAdapter(dadosEstadosBrasilUtil.getTabelaDeCasos(), this)
    }
    private val reportDadosRepository by lazy {
        ReportDadosRepository()
    }
    private val dadosEstadosBrasilUtil = DadosEstadosBrasilUtil(reportDadosRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        buscaDadosEstadosBrasil {
            configuraEstadosBrasilAdapter()
        }
        configuraAcaoBotaoVerTudo()
        configuraSpinner()
        configuraCliqueHeaderEstado()
        configuraCliqueHeaderValor()
        configuraCliqueListaDeMedidasEstados()
        configuraAdapterDasNoticias()
    }

    private fun configuraAdapterDasNoticias() {
        NoticiasRepository().buscaNoticiasDeCovid19({
            dashboard_noticias.adapter = NoticiasAdapter(this, it)
        },
            {
                erroAoTentarConectarComServidor(this)
            })
    }

    private fun configuraCliqueListaDeMedidasEstados() {
        configuraCliqueDaMedidaEstadosMorte()
        configuraCliqueDaMedidaEstadosCasos()
        configuraCliqueDaMedidaEstadosRecuperados()
    }

    private fun configuraCliqueDaMedidaEstadosRecuperados() {
        dashboard_lista_medidas_estados_recuperados.setOnCheckedChangeListener { buttonView, isChecked ->
            configuraSelecaoUnicaDoBotao(buttonView, isChecked)
            dashboard_header_valor.text = getString(R.string.recuperados)
            estadosBrasilAdapter.atualiza(dadosEstadosBrasilUtil.getTabelaDeRecuperados())
        }
    }

    private fun configuraCliqueDaMedidaEstadosCasos() {
        dashboard_lista_medidas_estados_casos.setOnCheckedChangeListener { buttonView, isChecked ->
            configuraSelecaoUnicaDoBotao(buttonView, isChecked)
            dashboard_header_valor.text = getString(R.string.casos)
            estadosBrasilAdapter.atualiza(dadosEstadosBrasilUtil.getTabelaDeCasos())
        }
    }

    private fun configuraCliqueDaMedidaEstadosMorte() {
        dashboard_lista_medidas_estados_mortes.setOnCheckedChangeListener { buttonView, isChecked ->
            configuraSelecaoUnicaDoBotao(buttonView, isChecked)
            dashboard_header_valor.text = getString(R.string.mortes)
            estadosBrasilAdapter.atualiza(dadosEstadosBrasilUtil.getTabelaDeMortes())
        }
    }

    private fun configuraSelecaoUnicaDoBotao(
        buttonView: CompoundButton,
        isChecked: Boolean
    ) {
        if (isChecked) {
            dashboard_lista_medidas_estados_layout.children.iterator().forEach {
                val botao = it as ToggleButton
                if (botao != buttonView) {
                    botao.isChecked = false
                    botao.isEnabled = true
                }
            }
            buttonView.isEnabled = false
        }
    }

    private fun configuraCliqueHeaderEstado() {
        dashboard_header_estado.setOnClickListener {
            val ordem = estadosBrasilAdapter.ordenaPor(ESTADO)
            atualizaDrawable(dashboard_header_estado, ordem)
            limpaDrawables(dashboard_header_valor)
        }
    }

    private fun configuraCliqueHeaderValor() {
        dashboard_header_valor.setOnClickListener {
            val ordem = estadosBrasilAdapter.ordenaPor(VALOR)
            atualizaDrawable(dashboard_header_valor, ordem)
            limpaDrawables(dashboard_header_estado)
        }
    }

    private fun atualizaDrawable(textView: TextView, ordem: Ordem) {
        if (ordem == CRESCENTE) {
            insereDrawable(textView, ic_seta_pra_cima_dashboard)
        } else {
            insereDrawable(textView, ic_seta_pra_baixo_dashboard)
        }
    }

    private fun insereDrawable(textView: TextView, @DrawableRes drawable: Int) {
        insereNo(textView, drawable, DIREITA)
    }

    private fun configuraAcaoBotaoVerTudo() {
        dashboard_altera_exibicao_estados.setOnClickListener {
            if (!estadosBrasilAdapter.listaExpandida) {
                estadosBrasilAdapter.exibeTodos()
                alteraBotaoExibidorDeEstadosParaEsconder()
            } else {
                estadosBrasilAdapter.exibeSoOs5Primeiros()
                alteraBotaoExibidorDeEstadosParaMostrarTudo()
            }
        }
    }

    private fun alteraBotaoExibidorDeEstadosParaMostrarTudo() {
        dashboard_altera_exibicao_estados.text = getString(R.string.mostrar_tudo)
        insereDrawable(dashboard_altera_exibicao_estados, ic_seta_pra_cima_dashboard)
    }

    private fun alteraBotaoExibidorDeEstadosParaEsconder() {
        dashboard_altera_exibicao_estados.text = getString(R.string.esconder)
        insereDrawable(dashboard_altera_exibicao_estados, ic_seta_pra_cima_dashboard)
    }

    private fun buscaDadosEstadosBrasil(buscou: (dadosEstadosBrasilUtil: DadosEstadosBrasilUtil) -> Unit) {
        reportDadosRepository.buscaDadosTodosEstadosBrasileiros({
            buscou(dadosEstadosBrasilUtil)
        }, {
            erroAoTentarConectarComServidor(this)
        })
    }

    private fun configuraEstadosBrasilAdapter() {
        estadosBrasilAdapter.exibeSoOs5Primeiros()
        estadosBrasilAdapter.ordenaPor(VALOR, DECRESCENTE)
        insereDrawable(dashboard_header_valor, ic_seta_pra_baixo_dashboard)
        dashboard_brasil_estados.adapter = estadosBrasilAdapter
    }

    private fun configuraSpinner() {
        exibeTodosOsPaisesNoSpinner()
        reportDadosRepository.buscaDadosDeTodosOsPaises({
            configuraAcaoDoSpinner()
        }, {
            erroAoTentarConectarComServidor(this)
        })
    }

    private fun configuraAcaoDoSpinner() {
        dashboard_paises_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    buscaDadosDePaisSelecionado(position)
                }

            }
    }

    private fun buscaDadosDePaisSelecionado(position: Int) {
        val dadosRetornados =
            reportDadosRepository.buscaPorPais(spinnerPaisesAdapter.paises[position])
        with(dadosRetornados) {
            dashboard_confimados.text = casosConfirmados.toString()
            dashboard_mortes.text = mortes.toString()
            dashboard_recuperados.text = recuperados.toString()
        }
    }

    private fun exibeTodosOsPaisesNoSpinner() {
        val paisRepository = PaisRepository()
        paisRepository.buscaTodosOsPaises({
            val listaAjustada = ajustaLista(it)
            spinnerPaisesAdapter = SpinnerPaisesAdapter(this, listaAjustada)
            dashboard_paises_spinner.adapter = spinnerPaisesAdapter
            selecionaBrasil()
        }, {
            erroAoTentarConectarComServidor(this)
        })
    }

    private fun ajustaLista(it: List<Pais>): List<Pais> {
        val listaComMundo = mutableListOf<Pais>()
        listaComMundo.addAll(it)
        listaComMundo.add(Pais("Mundo", ""))
        return listaComMundo.sortedWith(Comparator { o1, _ ->
            when (o1.name) {
                "Brazil" -> {
                    -1
                }
                "Mundo" -> {
                    -1
                }
                else -> {
                    1
                }
            }
        })
    }

    private fun selecionaBrasil() {
        dashboard_paises_spinner.setSelection(1)
    }
}

