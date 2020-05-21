package br.com.eliascoelho911.trackingcovid19.ui.adapter

import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.eliascoelho911.trackingcovid19.R
import br.com.eliascoelho911.trackingcovid19.model.Pais
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dashboard_paises_spinner.view.*

class SpinnerPaisesAdapter(private val context: Context, var paises: List<Pais>) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val pais = getItem(position)
        val view = convertView ?: from(context).inflate(
            R.layout.item_dashboard_paises_spinner,
            parent,
            false
        )
        Picasso.get().load(pais.bandeiraUrl).into(view.item_paises_spinner_bandeira)
        view.item_paises_spinner_pais.text = pais.name
        return view
    }

    override fun getItem(position: Int): Pais {
        return paises[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return paises.size
    }

}