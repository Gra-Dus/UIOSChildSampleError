//
//  RecoveryPasswordView.swift
//  iosApp
//
//  Created by Компьютер on 1/24/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RegistrationView: View {
    var viewModel: RegistrationViewModel
    @SwiftUI.State private var phoneNumber: String = ""
    @SwiftUI.State private var errorText: String = ""
    @SwiftUI.State private var isPhoneError: Bool = false

    init(viewModel: RegistrationViewModel) {
        self.viewModel = viewModel
    }

    var body: some View {
        ZStack {
            VStack(alignment: .center) {
                VStack(alignment: .leading, spacing: 24) {
                    Text("Strings.Registration.subtitleUnderField")
                        .foregroundColor(.gray)
                        .offset(x: 12)
                        .multilineTextAlignment(.leading)
                        .lineLimit(nil)
                    Spacer(minLength: 250)
                    Spacer()
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .ignoresSafeArea(.container)
       
    }
}
