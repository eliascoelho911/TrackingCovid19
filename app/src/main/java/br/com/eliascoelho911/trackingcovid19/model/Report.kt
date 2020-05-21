package br.com.eliascoelho911.trackingcovid19.model

import com.google.gson.annotations.SerializedName

data class Report(
    val data: List<Dados>
) {
    data class Dados(
        @SerializedName(value = "state", alternate = ["country"]) val nome: String,
        @SerializedName(value = "cases", alternate = ["confirmed"]) var casosConfirmados: Long,
        @SerializedName(value = "deaths") var mortes: Long,
        @SerializedName(value = "refuses", alternate = ["recovered"]) var recuperados: Long,
        @SerializedName(value = "datetime", alternate = ["updated_at"]) val atualizadoAte: String
    )
}