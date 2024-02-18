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
    let authManager = IOSInjectionHelper().authManager
    
    var body: some View {
        ComposeView()
                .ignoresSafeArea()

                // Handle incoming url configured in our app
                .onOpenURL { incomingURL in
                    print("App was opened via URL: \(incomingURL)")
                    handleIncomintUrl(incomingURL,authManager: authManager)
                }
    }
}


private func handleIncomintUrl(_ url: URL,authManager:AuthManager) {
    guard url.scheme == "https" else {
        return
    }
    guard url.host == "jerryokafor.com" else {
        return
    }

    guard let components = URLComponents(url: url, resolvingAgainstBaseURL: true) else {
        print("Invalid URL")
        return
    }


    let path = components.path

    switch path {
    case "/smshare/auth/callback":
        guard let code = components.queryItems?.first(where: { $0.name == "code" })?.value,
              let state = components.queryItems?.first(where: { $0.name == "state" })?.value
        else {
            print("Invalide respon")
            return
        }

        print("Using Code: \(code) and State: \(state)")

    default:
        print("Unhandled path: \(path)")

    }

}

