package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

class BottomSheetSample : Screen {
    @Composable
    override fun Content() {
        Surface {
            Column(modifier = Modifier.height(500.dp).fillMaxWidth().padding(16.dp)) {
                Text("This is a bottom sheet")
            }
        }
    }

}