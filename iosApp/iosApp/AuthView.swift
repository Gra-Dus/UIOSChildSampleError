//
//  AuthView.swift
//  iosApp
//
//  Created by Компьютер on 1/24/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AuthView: View {
    var vm: AuthViewModel
    @SwiftUI.State private var password: String = ""
    @SwiftUI.State private var phoneNumber: String = ""
    @SwiftUI.State var errorText: String = ""
    @SwiftUI.State var isPhoneError: Bool = false
    @SwiftUI.State var isPasswordError: Bool = false

    init(vm: AuthViewModel) {
        self.vm = vm
    }

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                VStack(spacing: 24) {
                }
                Spacer(minLength: 200)
                bottomView
                Spacer()
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .ignoresSafeArea(.container)
        }
    }

    private var bottomView: some View {
        VStack(spacing: 24) {
            Button(action: {
                vm.setEvent(event: AuthEventRegistration())}
            ) {
                    Text("goToReg")
                        .frame(maxWidth: .infinity)
                }

        }
    }

    var textError: some View {
        Text(errorText)
            .foregroundColor(.blue)
    }
}

