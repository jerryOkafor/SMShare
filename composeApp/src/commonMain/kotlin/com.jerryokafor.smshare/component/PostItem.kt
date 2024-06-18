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

package com.jerryokafor.smshare.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.jerryokafor.smshare.screens.compose.ComposeControlButton
import com.jerryokafor.smshare.theme.HalfVerticalSpacer
import com.jerryokafor.smshare.theme.OneVerticalSpacer
import com.jerryokafor.smshare.theme.TwoHorizontalSpacer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_linkedin
import smshare.composeapp.generated.resources.title_delete
import smshare.composeapp.generated.resources.title_edit

@Composable
fun PostItem(
    isEditable: Boolean = true,
    isScheduleAllowed: Boolean = true,
    isDeleteAllowed: Boolean = true,
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onScheduleClick: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.clip(RoundedCornerShape(5.dp)).size(32.dp).aspectRatio(1f),
                painter = painterResource(Res.drawable.ic_linkedin),
                contentDescription = ""
            )
            TwoHorizontalSpacer()
            Text(
                text = "Hendrux Paints created this on 12/23/2024",
                style = MaterialTheme.typography.labelLarge
            )
        }
        HalfVerticalSpacer()
        HorizontalDivider(thickness = 0.5.dp)

        Text(
            text = "Just closed a \$10K deal \uD83E\uDD73\n" +
                    "\n" +
                    "No sales, no email, no \"jump on a quick call\"\n" +
                    "\n" +
                    "The customer found my product, signed up, and paid via credit card. " +
                    "All self-served. It's just the best type of revenue.\n" +
                    "\n" +
                    "Here is a quick story about how I turned my weekend side project into a " +
                    "business:\n",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        OneVerticalSpacer()
        HorizontalDivider(thickness = 0.5.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),//.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isDeleteAllowed) {
                ComposeControlButton(
                    imageVector = Icons.Default.Delete,
                    text = stringResource(Res.string.title_delete),
                    color = MaterialTheme.colorScheme.error,
                    iconTintcolor = MaterialTheme.colorScheme.error,
                    onClick = onDeleteClick,
                )
            }
            if (isEditable) {
                ComposeControlButton(
                    imageVector = Icons.Default.Edit,
                    text = stringResource(Res.string.title_edit),
                    onClick = onEditClick,
                )
            }
            if (isScheduleAllowed) {
                ComposeControlButton(
                    imageVector = Icons.Default.Queue,
                    text = stringResource(Res.string.title_delete),
                    onClick = onScheduleClick,
                )
            }
        }
    }
}