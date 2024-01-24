package com.gra_dus.uioschildsampleerror.components.registration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RegistrationComponent {
    val registrationChildStack: Value<ChildStack<*, RegistrationChild>>

    sealed class RegistrationChild {
        class Registration(val component: RegistrationViewModel) : RegistrationChild()
    }
}

class RegistrationComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, RegistrationComponent {

    private val navigation = StackNavigation<RegistrationConfig>()

    private val _registrationChildStack =
        childStack(
            source = navigation,
            serializer = RegistrationConfig.serializer(),
            initialConfiguration = RegistrationConfig.Registration,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val registrationChildStack: Value<ChildStack<*, RegistrationComponent.RegistrationChild>> =
        _registrationChildStack

    private fun createChild(
        config: RegistrationConfig,
        componentContext: ComponentContext,
    ): RegistrationComponent.RegistrationChild = when (config) {
        is RegistrationConfig.Registration ->
            RegistrationComponent.RegistrationChild.Registration(
                RegistrationViewModel(
                    componentContext = componentContext
                )
            )
    }


    @Serializable
    private sealed class RegistrationConfig {

        @Serializable
        data object Registration : RegistrationConfig()
    }
}