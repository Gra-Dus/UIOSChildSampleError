package com.gra_dus.uioschildsampleerror.components.auth

import com.arkivanov.decompose.ComponentContext
import com.gra_dus.uioschildsampleerror.base.ViewModel
import com.gra_dus.uioschildsampleerror.base.checkByRegex
import com.gra_dus.uioschildsampleerror.base.checkMax
import com.gra_dus.uioschildsampleerror.base.checkMin
import kotlinx.coroutines.launch

class AuthViewModel(
    componentContext: ComponentContext,
    private val onRegistration: () -> Unit,
) : ViewModel<AuthEvent, AuthState, AuthEffect>(componentContext) {

    override fun createInitialState() = AuthState()

    override fun handleEvent(event: AuthEvent) {
        when (event) {

            is AuthEvent.Enter -> {
                launch {
                    val passwordError = isPasswordHasError(event.password)
                    val phoneError = isPhoneHasError(event.phone)
                    when {
                        passwordError != null -> {
                            setEffect { passwordError }
                        }

                        phoneError != null -> {
                            setEffect { phoneError }
                        }

                        else -> {
                            setEffect { AuthEffect.EnterSucceed }
                        }
                    }

                }
            }

            is AuthEvent.ForgotPassword -> {
                setEffect { AuthEffect.ForgotPasswordSucceed }
            }

            is AuthEvent.Registration -> {
                setEffect { AuthEffect.RegistrationSucceed }
                onRegistration.invoke()
            }

            is AuthEvent.ChangePassword -> {
                setState { copy(password = event.value) }
            }

            is AuthEvent.ChangePhone -> {
                setState { copy(phone = event.value) }
            }
        }
    }

    private fun isPasswordHasError(password: String = currentState.password): AuthEffect.PasswordWarning? {
        if (password.checkMin(MIN_PASSWORD_COUNT))
            return AuthEffect.PasswordWarning(MIN_PASSWORD_COUNT_WARNING)
        if (password.checkMax(MAX_PASSWORD_COUNT))
            return AuthEffect.PasswordWarning(MAX_PASSWORD_COUNT_WARNING)
        if (password.checkByRegex(AVAILABLE_SYMBOLS.toRegex()))
            return AuthEffect.PasswordWarning(AVAILABLE_SYMBOLS_WARNING)
        return null
    }

    private fun isPhoneHasError(phone: String = currentState.phone): AuthEffect.PhoneWarning? {
        if (phone.checkMax(MAX_PHONE_COUNT)) return AuthEffect.PhoneWarning(MAX_PHONE_COUNT_WARING)
        return null
    }

    companion object {
        const val MAX_PHONE_COUNT = 11
        const val MIN_PASSWORD_COUNT = 8
        const val MAX_PASSWORD_COUNT = 16
        const val AVAILABLE_SYMBOLS = "[A-Za-z0-9!\"#\$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]"
        const val MAX_PHONE_COUNT_WARING = "max_phone_count_warning"
        const val MIN_PASSWORD_COUNT_WARNING = "min_password_count_warning"
        const val MAX_PASSWORD_COUNT_WARNING = "max_password_count_warning"
        const val AVAILABLE_SYMBOLS_WARNING = "available_symbols_warning"
    }

}