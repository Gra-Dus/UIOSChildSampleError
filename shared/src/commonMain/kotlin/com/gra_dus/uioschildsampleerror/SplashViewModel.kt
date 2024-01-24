package com.gra_dus.uioschildsampleerror

import com.arkivanov.decompose.ComponentContext
import com.gra_dus.uioschildsampleerror.base.ViewModel

class SplashViewModel(
    componentContext: ComponentContext, private val onNextScreen: () -> Unit) :
    ViewModel<SplashEvent, SplashState, SplashEffect>(componentContext),
    ComponentContext by componentContext {
    override fun createInitialState(): SplashState = SplashState

    override fun handleEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.OpenNextScreen -> onNextScreen.invoke()
        }
    }

}