package com.gra_dus.uioschildsampleerror

import com.gra_dus.uioschildsampleerror.base.UIEffect
import com.gra_dus.uioschildsampleerror.base.UIEvent
import com.gra_dus.uioschildsampleerror.base.UIState

data object SplashState : UIState

sealed interface SplashEvent : UIEvent {
    data object OpenNextScreen : SplashEvent
}

sealed interface SplashEffect : UIEffect