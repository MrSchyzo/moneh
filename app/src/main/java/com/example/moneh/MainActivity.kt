package com.example.moneh

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneh.ui.composables.Chart
import com.example.moneh.ui.composables.DataPoint
import com.example.moneh.ui.model.Summary
import com.example.moneh.ui.theme.MonehTheme
import com.example.moneh.ui.utils.hsv

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
        }
    }
}

@Composable
@Preview(name = "lightSummary")
fun Summary(summary: Summary = Summary.sampleAssets()) {
    val dataPoints = summary.assets.assets.map { DataPoint(id = it.id, value = it.amount, label = it.name) }
    val colorOverrides = dataPoints.mapIndexed { idx, point ->
        point.id to hsv(hue = (340 * idx % 360).toFloat(), value = 0.75f, saturation = 0.5f)
    }.toMap()
    val context = LocalContext.current
    val total = dataPoints.sumOf(DataPoint<String>::value).toFloat()

    var show by remember {
        mutableStateOf(0f)
    }
    val amount by animateFloatAsState(
        targetValue = show,
        animationSpec = tween(2000)
    )

    LaunchedEffect(Unit) {
        show = total
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Text(text = "Breadcrumbs > in > here > !", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        Chart(
            dataPoints = dataPoints,
            colorOverrides = colorOverrides,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(24.dp)
                .aspectRatio(1f)
        ) {
            Toast.makeText(context, "Selected ID $it", Toast.LENGTH_SHORT).show()
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Text(text = "Total: € ${"%.2f".format(amount)}", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            dataPoints.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(10.dp)
                        .clickable {
                            Toast.makeText(context, "Selected ID ${it.id}", Toast.LENGTH_SHORT)
                                .show()
                        }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.1f),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(1.dp))
                                .background(colorOverrides[it.id] ?: Color.Gray)
                                .size(18.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            Text(it.label, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Amount(it.value.toFloat())
                        }
                    }
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun AnimatedAmount(
    amount: Float
) {
    var show by remember {
        mutableStateOf(0f)
    }
    val value by animateFloatAsState(
        targetValue = show,
        animationSpec = tween(2000)
    )

    LaunchedEffect(Unit) {
        show = amount
    }

    Amount(amount)
}

@Composable
fun Amount(
    value: Float
) {
    Text("€ %.2f".format(value), fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
}

@Composable
@Preview(
    name = "Light"
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun DefaultPreview() {
    MonehTheme {
        Summary(summary = Summary.sampleAssets())
        // Conversation(SampleData.conversationSample)
    }
}
