import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import injection.desktopModule
import injection.initKoin
import org.koin.dsl.module


private val koin = initKoin {
    modules(desktopModule)
}.koin

fun main() = application {
    val greeting = koin.get<Greeting>()
    println("Desktop: ${greeting.greet()}")

    Window(onCloseRequest = ::exitApplication, title = "SM Share") {
        App()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}