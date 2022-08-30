package com.gst.synccalender.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.gst.synccalender.R
import com.gst.synccalender.utils.network.State

/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class MultiStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var contentView: View? = null

    private var loadingView: View? = null

    private var errorView: View? = null

    private var emptyView: View? = null

    var listener: StateListener? = null

    var animateLayoutChanges: Boolean = false

    var animationDuration = resources.getInteger(android.R.integer.config_mediumAnimTime)

    var viewState: State = State.CONTENT
        set(value) {
            val previousField = field

            if (value != previousField) {
                field = value
                setView(previousField)
                listener?.onStateChanged(value)
            }
        }

    init {
        val inflater = LayoutInflater.from(getContext())
        val multiStateViewAttr =
            getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView)

        val loadingViewResId =
            multiStateViewAttr.getResourceId(
                R.styleable.MultiStateView_loadingView,
                R.layout.view_state_loading
            )
        if (loadingViewResId > -1) {
            val inflatedLoadingView = inflater.inflate(loadingViewResId, this, false)
            loadingView = inflatedLoadingView
            loadingView?.hide()
            addView(inflatedLoadingView, inflatedLoadingView.layoutParams)
        }

        val emptyViewResId =
            multiStateViewAttr.getResourceId(
                R.styleable.MultiStateView_emptyView,
                R.layout.view_state_empty
            )
        if (emptyViewResId > -1) {
            val inflatedEmptyView = inflater.inflate(emptyViewResId, this, false)
            emptyView = inflatedEmptyView
            emptyView?.hide()
            addView(inflatedEmptyView, inflatedEmptyView.layoutParams)
        }

        val errorViewResId =
            multiStateViewAttr.getResourceId(
                R.styleable.MultiStateView_errorView,
                R.layout.view_state_error
            )
        if (errorViewResId > -1) {
            val inflatedErrorView = inflater.inflate(errorViewResId, this, false)
            errorView = inflatedErrorView
            errorView?.hide()
            addView(inflatedErrorView, inflatedErrorView.layoutParams)
        }

        viewState = State.CONTENT
        animateLayoutChanges =
            multiStateViewAttr.getBoolean(R.styleable.MultiStateView_animateViewChanges, false)
        multiStateViewAttr.recycle()
    }

    /**
     * Returns the [View] associated with the [State]
     *
     * @param state The [State] with to return the view for
     * @return The [View] associated with the [State], null if no view is present
     */
    fun getView(state: State): View? {
        return when (state) {
            State.LOADING -> loadingView
            State.CONTENT -> contentView
            State.EMPTY -> emptyView
            State.ERROR -> errorView
        }
    }

    /**
     * Sets the view for the given view state
     *
     * @param view          The [View] to use
     * @param state         The [State]to set
     * @param switchToState If the [State] should be switched to
     */
    fun setViewForState(view: View, state: State, switchToState: Boolean = false) {
        when (state) {
            State.LOADING -> {
                if (loadingView != null) removeView(loadingView)
                loadingView = view
                addView(view)
            }

            State.EMPTY -> {
                if (emptyView != null) removeView(emptyView)
                emptyView = view
                addView(view)
            }

            State.ERROR -> {
                if (errorView != null) removeView(errorView)
                errorView = view
                addView(view)
            }

            State.CONTENT -> {
                if (contentView != null) removeView(contentView)
                contentView = view
                addView(view)
            }
        }

        if (switchToState) viewState = state
    }

    /**
     * Sets the [View] for the given [State]
     *
     * @param layoutRes     Layout resource id
     * @param state         The [State] to set
     * @param switchToState If the [State] should be switched to
     */
    fun setViewForState(@LayoutRes layoutRes: Int, state: State, switchToState: Boolean = false) {
        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        setViewForState(view, state, switchToState)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (contentView == null) throw IllegalArgumentException("Content view is not defined")

        when (viewState) {
            State.CONTENT -> setView(State.CONTENT)
            else -> contentView?.hide()
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return when (val superState = super.onSaveInstanceState()) {
            null -> superState
            else -> SavedState(superState, viewState)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            viewState = state.state
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    /* All of the addView methods have been overridden so that it can obtain the content view via XML
     It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     any of the setViewForState methods to set views for their given ViewState accordingly */
    override fun addView(child: View) {
        if (isValidContentView(child)) contentView = child
        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index, params)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, params)
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, width, height)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams,
        preventRequestLayout: Boolean
    ): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    /**
     * Checks if the given [View] is valid for the Content View
     *
     * @param view The [View] to check
     * @return
     */
    private fun isValidContentView(view: View): Boolean {
        return if (contentView != null && contentView !== view) {
            false
        } else view != loadingView && view != errorView && view != emptyView
    }

    private fun View?.fadeOut() {
        ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f).setDuration(animationDuration.toLong())
            .start()
    }

    private fun View?.fadeIn() {
        ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).setDuration(animationDuration.toLong())
            .start()
    }

    private fun animateLayoutChange(view: View?, previousState: State) {
        if (animateLayoutChanges && viewState != previousState) {
            getView(previousState).fadeOut()
            view.fadeIn()
        }

        getView(previousState)?.hide()
        view?.show()
    }

    /**
     * Shows the [View] based on the [State]
     */
    private fun setView(previousState: State) {
        when (viewState) {
            State.LOADING -> {
                requireNotNull(loadingView).apply {
                    animateLayoutChange(loadingView, previousState)
//                    contentView?.visibility(false)
//                    errorView?.visibility(false)
//                    emptyView?.visibility(false)
                }
            }

            State.EMPTY -> {
                requireNotNull(emptyView).apply {
                    animateLayoutChange(emptyView, previousState)
//                    contentView?.visibility(false)
//                    errorView?.visibility(false)
//                    loadingView?.visibility(false)
                }
            }

            State.ERROR -> {
                requireNotNull(errorView).apply {
                    animateLayoutChange(errorView, previousState)
//                    contentView?.visibility(false)
//                    loadingView?.visibility(false)
//                    emptyView?.visibility(false)
                }
            }

            State.CONTENT -> {
                requireNotNull(contentView).apply {
                    animateLayoutChange(contentView, previousState)
//                    loadingView?.visibility(false)
//                    errorView?.visibility(false)
//                    emptyView?.visibility(false)
                }
            }
        }
    }

    interface StateListener {
        /**
         * Callback for when the [State] has changed
         *
         * @param viewState The [State] that was switched to
         */
        fun onStateChanged(viewState: State)
    }

    private class SavedState : BaseSavedState {
        internal val state: State

        constructor(superState: Parcelable, state: State) : super(superState) {
            this.state = state
        }

        constructor(parcel: Parcel) : super(parcel) {
            state = parcel.readSerializable() as State
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSerializable(state)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}