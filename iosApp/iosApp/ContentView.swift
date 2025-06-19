import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
            // Handle incoming url configured in our app
            .onOpenURL { incomingURL in
                print("App was opened via URL: \(incomingURL)")
                ExternalUriHandler.shared.onNewUri(uri: incomingURL.absoluteString)
            }
    }
}



