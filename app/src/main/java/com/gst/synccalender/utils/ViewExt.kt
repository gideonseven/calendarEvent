package com.gst.synccalender.utils

import android.view.View


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}