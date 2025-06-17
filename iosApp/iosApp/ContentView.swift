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
    let appViewModel  = IOSInjectionHelper().appViewModel
    
    var body: some View {
        ComposeView()
            .ignoresSafeArea()
            // Handle incoming url configured in our app
            .onOpenURL { incomingURL in
                print("App was opened via URL: \(incomingURL)")
                if (incomingURL.path.hasPrefix("/smshare/auth/callback")) {
                    let components = URLComponents(url: incomingURL, resolvingAgainstBaseURL: false)
                    let queryItems = components?.queryItems ?? []
                    print("Query Items: \(queryItems)")

                    if  let code = queryItems.first(where: { $0.name == "code" })?.value{
                        let state = queryItems.first(where: { $0.name == "state" })?.value
                        print("Using Auth Code: \(code)")
                        print("Using Auth State: \(state)")
                        appViewModel.authenticateChannel(code: code, state: state)
                    }
                    
                }
            }
    }
}



