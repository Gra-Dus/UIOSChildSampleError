//
//  AppDelegate.swift
//  iosApp
//
//  Created by Компьютер on 1/24/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import UIKit
import shared

class AppDelegate: NSObject, UIApplicationDelegate {

    lazy var root: RootComponent = RootComponentImpl(componentContext: DefaultComponentContext(lifecycle: ApplicationLifecycle()))
}
