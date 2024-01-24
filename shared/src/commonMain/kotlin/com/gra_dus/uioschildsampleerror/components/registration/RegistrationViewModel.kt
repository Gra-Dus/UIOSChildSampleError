package com.gra_dus.uioschildsampleerror.components.registration

import com.arkivanov.decompose.ComponentContext
import com.gra_dus.uioschildsampleerror.base.ViewModel

class RegistrationViewModel(
    componentContext: ComponentContext,
) : ViewModel<RegistrationEvent, RegistrationState, RegistrationEffect>(componentContext) {
    override fun createInitialState(): RegistrationState = RegistrationState()

    override fun handleEvent(event: RegistrationEvent) {
        when (event) {
            else -> {}
        }
    }
}