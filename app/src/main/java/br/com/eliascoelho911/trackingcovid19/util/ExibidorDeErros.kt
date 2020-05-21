package br.com.eliascoelho911.trackingcovid19.util

import android.content.Context
import android.widget.Toast
import br.com.eliascoelho911.trackingcovid19.R

class ExibidorDeErros {
    companion object {
        fun erroAoTentarConectarComServidor(context: Context) {
            Toast.makeText(
                context,
                context.getString(R.string.erro_conexao_com_servidor_falhou),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}