/*
 * MIT License
 *
 * Copyright (c) 2024 SM Share Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jerryokafor.smshare.screens.manageTags

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jerryokafor.smshare.SMShareBottomAppBarState
import com.jerryokafor.smshare.SMShareTopAppBarState
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed interface TagAction {
    val label: String
    val icon: ImageVector

    data class AddToFavorites(
        override val label: String,
        override val icon: ImageVector
    ) : TagAction

    data class AddTag(
        override val label: String,
        override val icon: ImageVector
    ) : TagAction

    data class RemoveTag(
        override val label: String,
        override val icon: ImageVector
    ) : TagAction
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTagsScreen(
    onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    onSetUpBottomAppBar: (SMShareBottomAppBarState?) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val currentOnSetupTopAppBar by rememberUpdatedState(onSetupTopAppBar)
    val currentOnSetUpBottomAppBar by rememberUpdatedState(onSetUpBottomAppBar)

    val viewModel: ManageTagsViewModel = viewModel()
    val tagsAndGroups by viewModel.tagsAndGroups.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onAddTagClick: () -> Unit = {

    }
    LaunchedEffect(true) {
        currentOnSetupTopAppBar(
            SMShareTopAppBarState {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Manage Tags - Back",
                            )
                        }
                    },
                    title = { Text("Manage Tags") },
                    actions = {
                        IconButton(onClick = onAddTagClick) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "Add Tag",
                            )
                        }
                    },
                )
            },
        )
        currentOnSetUpBottomAppBar(null)
    }

    EmptyTagsView()
//    Column(
//        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        tagsAndGroups.forEach { tagGroupsAndTags ->
//            LabeledBorderBox(
//                modifier = Modifier.wrapContentSize().padding(8.dp),
//                label = tagGroupsAndTags.group.name
//            ) {
//                FlowRow(
//                    modifier = Modifier.fillMaxWidth().padding(8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
//                ) {
//                    tagGroupsAndTags.tags.forEach { tag ->
//                        Box {
//                            var isContextMenuVisible by rememberSaveable {
//                                mutableStateOf(false)
//                            }
//                            val dropdownItems = listOf(
//                                TagAction.AddToFavorites(
//                                    "Add To Favorite",
//                                    icon = Icons.Outlined.Favorite
//                                ),
//                                TagAction.AddTag("Add Tag", icon = Icons.Outlined.Add),
//                                TagAction.RemoveTag("Remove", icon = Icons.Outlined.Delete),
//                            )
//                            SuggestionChip(
//                                onClick = { isContextMenuVisible = true },
//                                label = { Text(tag.tag) }
//                            )
//
//                            DropdownMenu(
//                                expanded = isContextMenuVisible,
//                                onDismissRequest = {
//                                    isContextMenuVisible = false
//                                }
//                            ) {
//                                dropdownItems.forEach {
//                                    DropdownMenuItem(
//                                        leadingIcon = {
//                                            Icon(
//                                                imageVector = it.icon,
//                                                contentDescription = it.label
//                                            )
//                                        },
//                                        onClick = {
////                                        onItemClick(it)
//                                            isContextMenuVisible = false
//                                        },
//                                        text = { Text(text = it.label) })
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//    }
}

const val tageRoute = "tags"

fun NavGraphBuilder.tagsScreen(
    onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    onSetUpBottomAppBar: (SMShareBottomAppBarState?) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    composable(route = tageRoute) {
        ManageTagsScreen(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar,
            onBackClick = onBackClick,
        )
    }
}

fun NavController.navigateToTags(navOptions: NavOptions? = null) {
    navigate(route = tageRoute, navOptions = navOptions)
}


@Preview
@Composable
fun LabeledBorderBoxPreview() {
    LabeledBorderBox(
        modifier = Modifier.size(200.dp).padding(8.dp),
        label = "Fitness"
    ) {}
}

@Composable
fun LabeledBorderBox(
    modifier: Modifier = Modifier,
    label: String,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        // Border box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(
                    border = BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column {
                Spacer(Modifier.height(8.dp))
                content()
            }
        }

        // Floating label
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = label,
                style = style,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun EmptyTagsView(onAddTagClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Emoji or image
            Text(
                text = "😊",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "All Empty",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtext
            Text(
                text = "You do not have any tags yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            //Add tags
            OutlinedButton(onClick = onAddTagClick) {
                Text("Add tag")
            }
        }
    }

}
