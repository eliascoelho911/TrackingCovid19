package br.com.eliascoelho911.trackingcovid19.retrofit.callback

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Callback<T>(
    private val quandoSucesso: (resultado: T) -> Unit,
    private val quandoFalha: (erro: String) -> Unit
) : Callback<T> {
    private val mensagemDeFalha = "Falha na comunicação com o servidor"

    override fun onFailure(call: Call<T>, t: Throwable) {
        quandoFalha(mensagemDeFalha)
        Log.i("falhou", t.toString())
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            if (response.body() != null) {
                quandoSucesso(response.body()!!)
            }
        } else {
            quandoFalha(response.code().toString())
        }
    }

}