package com.gst.synccalender.utils

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
abstract class AppActivity<T : ViewDataBinding>(@LayoutRes contentLayoutId: Int) :
    CoreActivity<T>(contentLayoutId) {

    override fun obsoletedApp(message: String?) {
        super.obsoletedApp(message)
    }

    override fun maintenance(message: String?) {
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
    }
}