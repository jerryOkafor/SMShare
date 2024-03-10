package component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6
import smshare.composeapp.generated.resources.ic_facebook


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChannelImage(
    modifier: Modifier = Modifier,
    avatar: Painter = painterResource(Res.drawable.avatar6),
    indicator: Painter = painterResource(Res.drawable.ic_facebook),
    contentDescription: String? = null
) {
    var imageSize by remember { mutableStateOf(IntSize.Zero) }
    Box(modifier = Modifier.wrapContentSize()
        .onGloballyPositioned {
            imageSize = it.size
        }) {
        Image(
            painter = avatar,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .aspectRatio(1f)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val path = Path()
                    path.addOval(
                        oval = Rect(
                            topLeft = Offset.Zero,
                            bottomRight = Offset(size.width, size.height)
                        )
                    )
                    onDrawWithContent {
                        clipPath(path) {
                            // this draws the actual image - if you don't call drawContent, it wont
                            // render anything
                            this@onDrawWithContent.drawContent()
                        }

                        val dotRadiusWithBorder = size.width / 5f

                        // Clip a white border for the content
                        drawCircle(
                            color = Color.Black,
                            radius = dotRadiusWithBorder,
                            center = Offset(
                                x = size.width - dotRadiusWithBorder,
                                y = size.height - dotRadiusWithBorder
                            ),
                            blendMode = BlendMode.Clear
                        )

//                        val dotRadius = dotRadiusWithBorder * 0.85f
//                        // draw the red circle indication
//                        drawCircle(
//                            color = Color(0xFFEF5350),
//                            radius = dotRadius,
//                            center = Offset(
//                                x = size.width - dotRadiusWithBorder,
//                                y = size.height - dotRadiusWithBorder
//                            )
//                        )
                    }

                }

        )

        val density = LocalDensity.current
        val dotRadiusWithBorder = imageSize.width / 5f
        val dotRadius = dotRadiusWithBorder * 0.95f

        val dotRadiusDp = with(density) { dotRadius.toDp() }
        val dotSizeDp = dotRadiusDp * 2

        val space = (dotRadiusWithBorder - dotRadius).dp

        Box(
            modifier = Modifier.matchParentSize()
                .padding(
                    bottom = space / 2.6f,
                    end = space / 2.6f
                )
        ) {
            Image(
                modifier = Modifier.size(dotSizeDp)
                    .clip(CircleShape).align(Alignment.BottomEnd),
                contentScale = ContentScale.FillHeight,
                painter = indicator,
                contentDescription = null
            )
        }
    }
}
