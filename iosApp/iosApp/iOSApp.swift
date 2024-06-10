import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
	init(){
        Injection_iosKt.doInitKoin()
	}

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
