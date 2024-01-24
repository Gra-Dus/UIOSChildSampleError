//
//  RootView.swift
//  iosApp
//
//  Created by Компьютер on 1/24/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RootView: View {
    let root: RootComponent

    var body: some View {
        StackView(
            stackValue: StateValue(root.rootChildStack),
            onBack: { _ in },
            childContent: {
                switch $0 {
                case let child as RootComponentRootChild.SplashRootScreen: SplashView(vm: child.component)
                case let child as RootComponentRootChild.AuthRootScreen:AuthComp(authComponent: child.component)
                default: EmptyView()
                }
            }, hideNavigationBar: true
        )
    }
}

struct AuthComp : View {
    let authComponent: AuthComponent
    init(authComponent: AuthComponent) {
        self.authComponent = authComponent
    }
    var body: some View {
        StackView(stackValue: StateValue(authComponent.authChildStack), onBack: {_ in}, childContent: {
            switch $0{
            case let child as AuthComponentAuthChild.Auth: AuthView(vm: child.component)
            case let child as AuthComponentAuthChild.Registration: RegistrationComp(registrationComponent:child.component)
            default:
                EmptyView()
            }
        }, hideNavigationBar: true)
    }
}

struct RegistrationComp : View{
    let registrationComponent: RegistrationComponent
    var body: some View{
        StackView(stackValue: StateValue(registrationComponent.registrationChildStack),
                  onBack: {_ in},
                  childContent: {
            switch $0{
            case let child as RegistrationComponentRegistrationChild.Registration: RegistrationView(viewModel: child.component)
            default:
                EmptyView()
            }
        }, hideNavigationBar: true)
    }
}
