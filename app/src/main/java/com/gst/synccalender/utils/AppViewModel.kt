package com.gst.synccalender.utils

/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
abstract class AppViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> :
    CoreViewModel<Event, State, Effect>()