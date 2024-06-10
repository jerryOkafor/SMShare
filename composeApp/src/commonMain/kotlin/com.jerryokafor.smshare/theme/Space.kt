@file:Suppress("TooManyFunctions", "MatchingDeclarationName")

package com.jerryokafor.smshare.theme

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SMShareSpacing(
    val none: Dp = 0.dp,
    val quarter: Dp = 2.dp,
    val half: Dp = 4.dp,
    val one: Dp = 8.dp,
    val oneAndHalf: Dp = 12.dp,
    val two: Dp = 16.dp,
    val twoAndaHalf: Dp = 20.dp,
    val three: Dp = 24.dp,
    val four: Dp = 32.dp,
    val five: Dp = 40.dp,
    val fiveAndHalf: Dp = 44.dp,
    val six: Dp = 48.dp,
    val seven: Dp = 56.dp,
    val eight: Dp = 64.dp,
    val nine: Dp = 72.dp,
    val ten: Dp = 80.dp,
    val twelve: Dp = 96.dp,
)

@Composable
fun QuarterVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.quarter))
}

@Composable
fun HalfVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.half))
}

@Composable
fun OneVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.one))
}

@Composable
fun OneAndHalfVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.oneAndHalf))
}

@Composable
fun TwoVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.two))
}

@Composable
fun TwoAndHalfVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.twoAndaHalf))
}

@Composable
fun ThreeVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.three))
}

@Composable
fun FourVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.four))
}

@Composable
fun FiveVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.five))
}

@Composable
fun SixVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.six))
}

@Composable
fun EightVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.eight))
}

@Composable
fun TenVerticaSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.ten))
}

@Composable
fun TwelveVerticalSpacer() {
    Spacer(modifier = Modifier.height(SMShareNkiri.spacing.twelve))
}

@Composable
fun ColumnScope.FillingSpacer() {
    Spacer(modifier = Modifier.weight(1f))
}

// Horizontal Spacers
@Composable
fun QuarterHorizontalSpacer() {
    Spacer(modifier = Modifier.width(SMShareNkiri.spacing.quarter))
}

@Composable
fun HalfHorizontalSpacer() {
    Spacer(modifier = Modifier.width(SMShareNkiri.spacing.half))
}

@Composable
fun OneHorizontalSpacer() {
    Spacer(modifier = Modifier.width(SMShareNkiri.spacing.one))
}

@Composable
fun OneAndHalfHorizontalSpacer() {
    Spacer(modifier = Modifier.width(SMShareNkiri.spacing.oneAndHalf))
}

@Composable
fun TwoHorizontalSpacer() {
    Spacer(modifier = Modifier.width(SMShareNkiri.spacing.two))
}

@Composable
fun ThreeHorizontalSpacer() {
    Spacer(modifier = Modifier.width(SMShareNkiri.spacing.three))
}

@Composable
fun TwoAndHalfHorizontalSpacer() {
    Spacer(modifier = Modifier.width(SMShareNkiri.spacing.twoAndaHalf))
}

@Composable
fun RowScope.FillingSpacer() {
    Spacer(modifier = Modifier.weight(1f))
}

val LocalSMShareSpacing = staticCompositionLocalOf { SMShareSpacing() }
