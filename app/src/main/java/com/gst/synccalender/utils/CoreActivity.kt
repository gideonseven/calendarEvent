package com.gst.synccalender.utils

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.skydoves.bindables.BindingActivity
import timber.log.Timber


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
abstract class CoreActivity<T : ViewDataBinding> constructor(
    @LayoutRes contentLayoutId: Int
) : BindingActivity<T>(contentLayoutId) {
    /**
     * Open function to handle obsolete app version. In app base level, this should be overwritten to do the stuff.
     * For other reasons, [message] is passed for wording purpose , if any
     */
    open fun obsoletedApp(message: String?) {
        Timber.e("aa obsoleted App $message")
    }

    /**
     * Open function to handle maintenance app status. In app base level, this should be overwritten to do the stuff
     * For other reasons, [message] is passed for wording purpose, if any
     */
    open fun maintenance(message: String?) {
        Timber.e("aa maintenance $message")
    }
}