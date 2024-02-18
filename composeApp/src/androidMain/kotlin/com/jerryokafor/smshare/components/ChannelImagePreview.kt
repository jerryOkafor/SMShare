package com.jerryokafor.smshare.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import component.ChannelImage
import component.ChannelWithName
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun ChannelImagePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChannelImage(
                modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally),
                indicator = painterResource("ic_facebook.xml")
            )

            ChannelImage(
                modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally),
                indicator = painterResource("ic_linkedin.xml")
            )

            ChannelImage(
                modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally),
                indicator = painterResource("ic_instagram.xml")
            )

            ChannelImage(
                modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally),
                indicator = painterResource("ic_twitter.xml")
            )

            ChannelWithName(
                name = "Jerry Okafor",
                avatar = painterResource("avatar6.png"),
                indicator = painterResource("ic_facebook.xml")
            )
        }
    }
}