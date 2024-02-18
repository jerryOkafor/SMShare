import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
	init(){
        IOSInjectionKt.doInitKoin()
	}
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
	
}
