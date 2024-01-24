//
//  ObseravableValue.swift
//  iosApp
//
//  Created by Компьютер on 1/24/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import SwiftUI
import shared

public class ObservableValue<T : AnyObject> : ObservableObject {
    @Published
    var value: T

    private var cancellation: Cancellation?
    
    init(_ value: Value<T>) {
        self.value = value.value
        self.cancellation = value.observe { [weak self] value in self?.value = value }
    }

    deinit {
        cancellation?.cancel()
    }
}
