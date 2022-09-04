package com.example.moneh.ui.utils

import android.graphics.Color.argb
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun hsv(hue: Float, saturation: Float = 1f, value: Float = 1f, alpha: Float = 1f): Color =
    android.graphics.Color.HSVToColor(
        floatArrayOf(
            hue.coerceIn(0f, 360f),
            saturation.coerceIn(0f, 1f),
            value.coerceIn(0f, 1f)
        )
    ).let(::Color)
    .withAlpha(alpha)

private fun Color.toHsv(): FloatArray {
    val hsv = floatArrayOf(0f, 0f, 0f)
    val argb = this.toArgb()
    android.graphics.Color.RGBToHSV(
        android.graphics.Color.red(argb),
        android.graphics.Color.green(argb),
        android.graphics.Color.blue(argb),
        hsv
    )
    return hsv
}

fun Color.with(
    hue: ((Float) -> Float) = { it },
    value: ((Float) -> Float) = { it },
    saturation: ((Float) -> Float) = { it }
): Color {
    val hsv = toHsv()
    return android.graphics.Color.HSVToColor(
        floatArrayOf(
            hsv[0].let(hue).coerceIn(0f, 360f),
            hsv[1].let(saturation).coerceIn(0f, 1f),
            hsv[2].let(value).coerceIn(0f, 1f),
        )
    ).let(::Color)
}

fun Color.withAlpha(alpha: Float = 1f): Color =
    this.toArgb().withAlpha(alpha).let(::Color)

private fun Int.withAlpha(alpha: Float = 1f): Int =
    argb(
        (alpha * 255f).toInt().coerceIn(0, 255),
        android.graphics.Color.red(this),
        android.graphics.Color.green(this),
        android.graphics.Color.blue(this),
    )
