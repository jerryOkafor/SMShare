@file:Suppress("ktlint:standard:filename", "MatchingDeclarationName")

package com.jerryokafor.smshare.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class SMShareShape(
    val small: RoundedCornerShape = RoundedCornerShape(4.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(8.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val pill: RoundedCornerShape = RoundedCornerShape(percent = 50),
) {
    fun toMaterialShapes() = Shapes(
        small = small,
        medium = medium,
        large = large,
    )
}

internal val LocalIheNkiriShape = staticCompositionLocalOf { SMShareShape() }
