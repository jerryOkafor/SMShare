package screens.addNewConnection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewConnectionChannel(onBackPress: () -> Unit) {
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        val sheetState = rememberModalBottomSheetState()

        val onDismissRequest: () -> Unit = {
            scope.launch { sheetState.hide() }
                .invokeOnCompletion { onBackPress() }
        }

        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
        ) {
            // Sheet content
            Column(
                modifier =
                    Modifier.fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp),
            ) {
                Button(onClick = onDismissRequest) {
                    Text("Hide bottom sheet")
                }
            }
        }
    }
}

const val addNewConnectionRoute = "addNewConnection"

fun NavGraphBuilder.addNewConnectionScreen(onBackPress: () -> Unit) {
    composable(addNewConnectionRoute) {
        AddNewConnectionChannel(onBackPress = onBackPress)
    }
}

fun NavController.navigateToAddNewConnection(navOptions: NavOptions? = null) {
    navigate(route = addNewConnectionRoute, navOptions = navOptions)
}
