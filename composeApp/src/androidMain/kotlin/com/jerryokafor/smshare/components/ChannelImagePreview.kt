package com.jerryokafor.smshare.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import component.ChannelImage
import component.ChannelWithName
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6
import smshare.composeapp.generated.resources.ic_facebook
import smshare.composeapp.generated.resources.ic_instagram
import smshare.composeapp.generated.resources.ic_linkedin
import smshare.composeapp.generated.resources.ic_twitter

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
                indicator = painterResource(Res.drawable.ic_facebook)
            )

            ChannelImage(
                modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally),
                indicator = painterResource(Res.drawable.ic_linkedin)
            )

            ChannelImage(
                modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally),
                indicator = painterResource(Res.drawable.ic_instagram)
            )

            ChannelImage(
                modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally),
                indicator = painterResource(Res.drawable.ic_twitter)
            )

            ChannelWithName(
                name = "Jerry Okafor",
                avatar = painterResource(Res.drawable.avatar6),
                indicator = painterResource(Res.drawable.ic_facebook)
            )
        }
    }
}