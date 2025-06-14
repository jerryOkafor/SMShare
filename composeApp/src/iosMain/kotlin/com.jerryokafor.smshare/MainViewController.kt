import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.ComposeUIViewController
import com.jerryokafor.smshare.App

@Suppress("ktlint:standard:function-naming", "FunctionNaming")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun MainViewController() = ComposeUIViewController {
    val windowSizeClass = calculateWindowSizeClass()
    val useNavRail = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact
    App(shouldUseNavRail = useNavRail)
}
