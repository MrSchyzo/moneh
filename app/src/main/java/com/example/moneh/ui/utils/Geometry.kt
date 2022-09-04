package com.example.moneh.ui.utils

import androidx.compose.ui.geometry.Offset
import kotlin.math.atan2

fun Offset.withBottomOrigin(height: Float) =
    this.copy(y = height - this.y)

fun Offset.apartFrom(point: Offset) =
    this - point

fun Offset.toNorthClockwiseAngle() =
    atan2(this.x.toDouble(), this.y.toDouble()).let(Math::toDegrees).let {
        when {
            it < 0 -> it + 360f
            else -> it
        }
    }
