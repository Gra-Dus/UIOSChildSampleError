//
//  SplashView.swift
//  iosApp
//
//  Created by Компьютер on 1/24/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SplashView : View{
    var vm: SplashViewModel
    @SwiftUI.State private var drawingWidth = false
    @SwiftUI.State private var isAnimating = true
    @SwiftUI.State var percent: Int = 0
    @SwiftUI.State private var isShowingNextScreen = false
    init(vm: SplashViewModel) {
        self.vm = vm
    }
    var body: some View {
        VStack(alignment: .center) {
            Text("\(percent) %")
                .offset(y: -13)
                .foregroundColor(.white)
            HStack(spacing: 7) {
                progressBar
            }
        }
    }

    private var progressBar: some View {
        ZStack(alignment: .leading) {
            RoundedRectangle(cornerRadius: 45)
                .fill(.white)
            RoundedRectangle(cornerRadius: 45)
                .fill(.black)
                .frame(width: drawingWidth ? 205 : 0, alignment: .leading)
                .animation(.linear(duration: 5), value: drawingWidth)
        }.frame(width: 205, height: 3)
            .onAppear {
                startAnimating()
                drawingWidth.toggle()
            }
    }
    private func startAnimating() {
        Timer.scheduledTimer(withTimeInterval: 0.05, repeats: true) { timer in
            if percent < 100 {
                percent += 1
            } else {
                timer.invalidate()
                isAnimating = false
                vm.setEvent(event: SplashEventOpenNextScreen())
            }
        }
    }
    
}

