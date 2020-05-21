package br.com.eliascoelho911.trackingcovid19.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.eliascoelho911.trackingcovid19.R
import br.com.eliascoelho911.trackingcovid19.model.GNewsModel.Noticia
import br.com.eliascoelho911.trackingcovid19.ui.adapter.NoticiasAdapter.NoticiaViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_noticias.view.*
import java.text.SimpleDateFormat

class NoticiasAdapter(
    private val context: Context,
    private val noticias: List<Noticia>
) : Adapter<NoticiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        return NoticiaViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_noticias, parent, false)
            , context
        )
    }

    override fun getItemCount(): Int {
        return noticias.size
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        holder.vincula(noticias[position])
    }

    class NoticiaViewHolder(
        itemView: View,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("DefaultLocale")
        fun vincula(noticia: Noticia) {
            Picasso.get().load(noticia.imagemUrl).into(itemView.item_noticias_imagem)
            itemView.item_noticias_titulo.text = noticia.titulo
            val dataFormatadaNoFormatoBrasileiro = formataDataParaOFormatoBrasileiro(noticia)
            itemView.item_noticias_data.text = dataFormatadaNoFormatoBrasileiro
            itemView.item_noticias_jornal.text = noticia.jornal.nome.capitalize()
            abreANoticiaComOClique(noticia)
        }

        private fun abreANoticiaComOClique(noticia: Noticia) {
            itemView.setOnClickListener {
                val abrirNoticia = Intent(Intent.ACTION_VIEW)
                abrirNoticia.data = Uri.parse(noticia.url)
                context.startActivity(abrirNoticia)
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun formataDataParaOFormatoBrasileiro(noticia: Noticia): String {
            val formatoAmericano = SimpleDateFormat("yyyy-MM-dd")
            val dataNoFormatoAmericano =
                formatoAmericano.parse(noticia.dataDePublicacao.substring(0, 10))!!
            val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
            return formatoBrasileiro.format(dataNoFormatoAmericano)
        }

    }
}