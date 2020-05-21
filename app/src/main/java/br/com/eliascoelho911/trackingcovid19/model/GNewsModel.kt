package br.com.eliascoelho911.trackingcovid19.model

import com.google.gson.annotations.SerializedName

data class GNewsModel(
    @SerializedName(value = "articles") val noticias: List<Noticia>
) {
    data class Noticia(
        @SerializedName(value = "title") val titulo: String,
        @SerializedName(value = "description") val descricao: String,
        @SerializedName(value = "image") val imagemUrl: String,
        @SerializedName(value = "url") val url: String,
        @SerializedName(value = "publishedAt") val dataDePublicacao: String,
        @SerializedName(value = "source") val jornal: Jornal
    )

    data class Jornal(
        @SerializedName(value = "name") val nome: String,
        @SerializedName(value = "url") val url: String
    )
}

