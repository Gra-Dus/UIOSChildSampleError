package com.gra_dus.uioschildsampleerror.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.gra_dus.uioschildsampleerror.components.auth.AuthViewModel
import com.gra_dus.uioschildsampleerror.components.registration.RegistrationComponent
import com.gra_dus.uioschildsampleerror.components.registration.RegistrationComponentImpl
import kotlinx.serialization.Serializable

interface AuthComponent {
    val authChildStack: Value<ChildStack<*, AuthChild>>

    sealed class AuthChild {
        data class Auth(val component: AuthViewModel) : AuthChild()
        data class Registration(val component: RegistrationComponent) : AuthChild()
    }
}

class AuthComponentImpl(
    componentContext: ComponentContext,
    private val onEnter: () -> Unit
) : ComponentContext by componentContext, AuthComponent {

    private val navigation = StackNavigation<AuthChildConfig>()

    private val _authChildStack = childStack(
        key = "AuthComponent",
        source = navigation,
        serializer = AuthChildConfig.serializer(),
        initialConfiguration = AuthChildConfig.Auth,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val authChildStack: Value<ChildStack<*, AuthComponent.AuthChild>> = _authChildStack

    private fun createChild(
        config: AuthChildConfig,
        componentContext: ComponentContext,
    ): AuthComponent.AuthChild = when (config) {
        is AuthChildConfig.Auth -> {
            AuthComponent.AuthChild.Auth(
                AuthViewModel(
                    componentContext = componentContext,
                    onRegistration = {
                        navigation.bringToFront(AuthChildConfig.Registration)
                    }
                )
            )
        }

        is AuthChildConfig.Registration -> {
            AuthComponent.AuthChild.Registration(
                RegistrationComponentImpl(
                    componentContext = componentContext,
                )
            )
        }
    }

    @Serializable
    private sealed class AuthChildConfig {

        @Serializable
        data object Auth : AuthChildConfig()

        @Serializable
        data object Registration : AuthChildConfig()
    }

}