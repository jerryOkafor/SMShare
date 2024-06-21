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

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.jerryokafor.smshare.theme.OneVerticalSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview()
@Composable
fun AnalyticsItemPreview() {
    MaterialTheme {
        AnalyticsItem()
    }
}

@Composable
fun AnalyticsItem() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
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
            modifier = Modifier.padding(16.dp),
            maxLines = 3,
        )
        OneVerticalSpacer()
        HorizontalDivider(thickness = 0.5.dp)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            PostAnalytics(0, "Reach")
            PostAnalytics(0, "Likes")
            PostAnalytics(0, "Share")
            PostAnalytics(0, "Mentions")
            PostAnalytics(0, "Clicks")
        }
    }
}

@Composable
private fun PostAnalytics(
    count: Int,
    title: String,
) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
        Text(text = "$count")
        Text(text = title, style = MaterialTheme.typography.labelSmall)
    }
}
