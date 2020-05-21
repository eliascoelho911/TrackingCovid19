package br.com.eliascoelho911.trackingcovid19.util

import android.widget.TextView
import androidx.annotation.DrawableRes
import br.com.eliascoelho911.trackingcovid19.util.CompoundDrawablesUtil.Lado.*

class CompoundDrawablesUtil {
    companion object {
        fun insereNo(textView: TextView, @DrawableRes drawable: Int, lado: Lado) {
            val left = if (lado == ESQUERDA) {
                drawable
            } else {
                0
            }
            val right = if (lado == DIREITA) {
                drawable
            } else {
                0
            }
            val top = if (lado == CIMA) {
                drawable
            } else {
                0
            }
            val bottom = if (lado == BAIXO) {
                drawable
            } else {
                0
            }

            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        }

        fun limpaDrawables(textView: TextView) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }
    }

    enum class Lado {
        CIMA, BAIXO, ESQUERDA, DIREITA
    }
}