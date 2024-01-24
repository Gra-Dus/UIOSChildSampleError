package com.gra_dus.uioschildsampleerror

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.gra_dus.uioschildsampleerror.components.AuthComponent
import com.gra_dus.uioschildsampleerror.components.AuthComponentImpl
import kotlinx.serialization.Serializable

interface RootComponent {
    val rootChildStack: Value<ChildStack<*, RootChild>>

    sealed class RootChild {
        data class AuthRootScreen(val component: AuthComponent) : RootChild()
        data class SplashRootScreen(val component: SplashViewModel) : RootChild()
    }

}

class RootComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, RootComponent {

    private val navigation = StackNavigation<RootConfig>()
    private val _rootChildStack = childStack(
        key = "RootComponent",
        serializer = RootConfig.serializer(),
        source = navigation,
        initialConfiguration = RootConfig.SplashRootConfig,
        childFactory = ::childFactory
    )
    override val rootChildStack: Value<ChildStack<*, RootComponent.RootChild>>
        get() = _rootChildStack

    private fun childFactory(
        config: RootConfig,
        componentContext: ComponentContext,
    ): RootComponent.RootChild = when (config) {
        is RootConfig.AuthRootConfig -> RootComponent.RootChild.AuthRootScreen(
            AuthComponentImpl(
                componentContext = componentContext,
                onEnter = {                }
            )
        )

        is RootConfig.SplashRootConfig -> RootComponent.RootChild.SplashRootScreen(
            SplashViewModel(
                componentContext = componentContext,
                onNextScreen = {
                    navigation.replaceAll(RootConfig.AuthRootConfig)
                }
            )
        )
    }

    @Serializable
    sealed class RootConfig {

        @Serializable
        data object SplashRootConfig : RootConfig()

        @Serializable
        data object AuthRootConfig : RootConfig()
    }
}
