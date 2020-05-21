package br.com.eliascoelho911.trackingcovid19.model

data class Pais(
    val name: String,
    val alpha2Code: String
) {
    val bandeiraUrl: String get() {
        return "https://www.countryflags.io/$alpha2Code/flat/48.png"
    }
}
