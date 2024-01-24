package com.gra_dus.uioschildsampleerror.components.auth

import com.gra_dus.uioschildsampleerror.base.BaseError
import com.gra_dus.uioschildsampleerror.base.UIEffect
import com.gra_dus.uioschildsampleerror.base.UIEvent
import com.gra_dus.uioschildsampleerror.base.UIState

data class AuthState(
    val phone: String = "",
    val password: String = ""
) : UIState

sealed interface AuthEvent : UIEvent {
    data class ChangePhone(val value: String) : AuthEvent
    data class ChangePassword(val value: String) : AuthEvent
    data class Enter(val phone: String, val password: String) : AuthEvent
    data object ForgotPassword : AuthEvent
    data object Registration : AuthEvent
}

sealed interface AuthEffect : UIEffect {
    data class PasswordWarning(override val message: String) : BaseError(message), AuthEffect
    data class PhoneWarning(override val message: String) : BaseError(message), AuthEffect
    data object EnterSucceed : AuthEffect
    data object ForgotPasswordSucceed : AuthEffect
    data object RegistrationSucceed : AuthEffect
}
