package com.example.moneh.ui.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import com.example.moneh.ui.utils.andThen
import com.example.moneh.ui.utils.apartFrom
import com.example.moneh.ui.utils.hsv
import com.example.moneh.ui.utils.logError
import com.example.moneh.ui.utils.toNorthClockwiseAngle
import com.example.moneh.ui.utils.with
import com.example.moneh.ui.utils.withBottomOrigin
import kotlin.math.abs

data class DataPoint<ID>(val id: ID, val value: Double, val label: String)

@Composable
fun <ID> Chart(
    dataPoints: List<DataPoint<ID>>,
    modifier: Modifier = Modifier,
    colorOverrides: Map<ID, Color> = mapOf(),
    onSelection: (ID) -> Unit = {}
) {
    var targetSweep by remember { mutableStateOf(0f) }
    val currentSweepRange by animateFloatAsState(
        targetValue = targetSweep,
        animationSpec = tween(2000)
    )

    val total = dataPoints.sumOf { it.value }
    val angleBounds: List<Pair<Double, ID>> = dataPoints.foldIndexed(listOf()) { idx, list, it ->
        val last = list.lastOrNull()?.first ?: 0.0

        list + ((last + it.value * 360.0 / total) to it.id)
    }

    LaunchedEffect(Unit) {
        targetSweep = 360f
    }

    if (dataPoints.isEmpty()) {
        Canvas(modifier = modifier) {
            drawSlice(
                color = Color.White.with(value = { 0.95f }),
                startAngle = -90f,
                sweepAngle = currentSweepRange
            )
        }
        return
    }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                val center = this.size.center.toOffset()
                detectTapGestures(
                    onTap = { tap ->
                        if (currentSweepRange < 360f) return@detectTapGestures

                        val touchAngle =
                            tap.withBottomOrigin(this.size.height.toFloat())
                                .apartFrom(center)
                                .toNorthClockwiseAngle()

                        angleBounds.binarySearchBy(touchAngle) { bound ->
                            bound.first
                        }
                            .let { abs(it) - 1 }
                            .also(logError("Found"))
                            .let(angleBounds::get andThen { it.second })
                            .run(onSelection)
                    }
                )
            }
    ) {
        var start = -90f
        var hue = 0f
        dataPoints.forEach {
            val sweep = ((currentSweepRange * it.value / total)).toFloat()
            val hueSweep = ((360.0 / dataPoints.size)).toFloat()
            drawSlice(
                color = colorOverrides[it.id] ?: hsv(hue = hue, saturation = 0.5f, value = 0.75f),
                startAngle = start,
                sweepAngle = sweep
            )
            start = (start + sweep).coerceAtMost(360f)
            hue = (hue + hueSweep).coerceAtMost(359f)
        }
    }
}

private fun DrawScope.drawSlice(color: Color, startAngle: Float, sweepAngle: Float) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true
    )
    drawArc(
        color = color.with(value = { it / 2 }),
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        style = Stroke(width = 4f)
    )
}
