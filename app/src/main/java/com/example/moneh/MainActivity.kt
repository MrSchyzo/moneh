package com.example.moneh

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun Summary_(summary: Summary = Summary.sampleAssets()) {
    var visible by remember {mutableStateOf(false)}

    Column {
        val dataPoints = summary.assets.assets.map { DataPoint(id = it.id, value = it.amount, label = it.name) }
        val context = LocalContext.current
        Chart(
            dataPoints = dataPoints,
            colorOverrides = dataPoints.mapIndexed { idx, point ->
                point.id to hsv(hue = (340*idx % 360).toFloat(), value = 0.75f, saturation = 0.5f)
            }.toMap(),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(4.dp)
                .aspectRatio(1f)
        ) {
            Toast.makeText(context, "Selected ID $it", Toast.LENGTH_LONG).show()
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text("Something else", modifier = Modifier.clickable {
            visible = !visible
        })
    }
}

@Composable
@Preview(
    name = "Light",
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
fun DefaultPreview() {
    MonehTheme {
        Summary_(summary = Summary.sampleAssets())
        //Conversation(SampleData.conversationSample)
    }
}
