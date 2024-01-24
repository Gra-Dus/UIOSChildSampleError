package com.gra_dus.uioschildsampleerror.components.registration

import com.gra_dus.uioschildsampleerror.base.BaseError
import com.gra_dus.uioschildsampleerror.base.UIEffect
import com.gra_dus.uioschildsampleerror.base.UIEvent
import com.gra_dus.uioschildsampleerror.base.UIState

data class RegistrationState(
    val phone: String = ""
) : UIState {
}

sealed interface RegistrationEvent : UIEvent

sealed interface RegistrationEffect : UIEffect