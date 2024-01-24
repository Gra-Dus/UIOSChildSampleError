//
//  StackView.swift
//  iosApp
//
//  Created by Компьютер on 1/24/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import UIKit
import shared

struct StackView<T: AnyObject, Content: View>: View {
    @StateValue
    var stackValue: ChildStack<AnyObject, T>
    
    var onBack: (_ toIndex: Int32) -> Void

    @ViewBuilder
    var childContent: (T) -> Content
    var hideNavigationBar: Bool

    private var stack: [Child<AnyObject, T>] { stackValue.items }

    var body: some View {
        if #available(iOS 16.1, *) {
            NavigationStack(
                path: Binding(
                    get: { stack.dropFirst() },
                    set: { updatedPath in onBack(Int32(updatedPath.count)) }
                )
            ) {
                childContent(stack.first!.instance!)
                    .navigationDestination(for: Child<AnyObject, T>.self) {
                        childContent($0.instance!)
                    }
            }.ignoresSafeArea(.container)
        } else {
            StackInteropView(
                components: stack.map { $0.instance! },
                onBack: onBack,
                childContent: childContent,
                hideNavigationBar: hideNavigationBar
            )
        }
    }
}

private struct StackInteropView<T: AnyObject, Content: View>: UIViewControllerRepresentable {
    var components: [T]
    var onBack: (_ toIndex: Int32) -> Void
    var childContent: (T) -> Content
    var hideNavigationBar: Bool

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    func makeUIViewController(context: Context) -> UINavigationController {
        context.coordinator.syncChanges(self)
        let navigationController = CustomNavigationController(
            rootViewController: context.coordinator.viewControllers.first!, hideNavigationBar: hideNavigationBar)
        return navigationController
    }

    func updateUIViewController(_ navigationController: UINavigationController, context: Context) {
        context.coordinator.syncChanges(self)
        navigationController.setViewControllers(context.coordinator.viewControllers, animated: true)
    }

    private func createViewController(_ component: T, _ coordinator: Coordinator) -> NavigationItemHostingController {
        let controller = NavigationItemHostingController(rootView: childContent(component))
        controller.coordinator = coordinator
        controller.component = component
        controller.onBack = onBack
        return controller
    }

    class CustomNavigationController: UINavigationController {
        var hideNavigationBar: Bool

        init(rootViewController: UIViewController, hideNavigationBar: Bool) {
            self.hideNavigationBar = hideNavigationBar
            super.init(rootViewController: rootViewController)
        }

        required init?(coder aDecoder: NSCoder) {
            fatalError("init(coder:) has not been implemented")
        }

        override func viewDidLayoutSubviews() {
            super.viewDidLayoutSubviews()
            navigationBar.isHidden = hideNavigationBar
            view.frame = UIScreen.main.bounds
            view.backgroundColor = UIColor(.blue)
        }
    }

    class Coordinator: NSObject {
        var parent: StackInteropView<T, Content>
        var viewControllers = [NavigationItemHostingController]()
        var preservedComponents = [T]()

        init(_ parent: StackInteropView<T, Content>) {
            self.parent = parent
        }

        func syncChanges(_ parent: StackInteropView<T, Content>) {
            self.parent = parent
            let count = max(preservedComponents.count, parent.components.count)
            for index in 0..<count {
                if index >= parent.components.count {
                    viewControllers.removeLast()
                } else if index >= preservedComponents.count {
                    viewControllers.append(parent.createViewController(parent.components[index], self))
                } else if parent.components[index] !== preservedComponents[index] {
                    viewControllers[index] = parent.createViewController(parent.components[index], self)
                }
            }

            preservedComponents = parent.components
        }
    }

    class NavigationItemHostingController: UIHostingController<Content> {
        fileprivate(set) weak var coordinator: Coordinator?
        fileprivate(set) var component: T?
        fileprivate(set) var onBack: ((_ toIndex: Int32) -> Void)?

        override func viewDidAppear(_ animated: Bool) {
            super.viewDidAppear(animated)
            guard let components = coordinator?.preservedComponents else { return }
            guard let index = components.firstIndex(where: { $0 === component }) else { return }

            if index < components.count - 1 {
                onBack?(Int32(index))
            }
        }
    }
}

