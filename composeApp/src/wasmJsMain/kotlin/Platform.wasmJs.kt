class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

class WasmAuthManager : AuthManager {
    override fun authenticateUser(
        domain: String,
        clientId: String,
        redirectUri: String,
        scope: String,
        audience: String
    ) {

    }

}

actual fun buildAuthManager(): AuthManager = WasmAuthManager()