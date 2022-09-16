package com.example.moneh

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.composable
import com.example.moneh.ui.composables.LoginScreen
import com.example.moneh.ui.composables.SplashScreen
import com.example.moneh.ui.composables.SummaryScreen
import com.example.moneh.ui.model.Summary
import com.example.moneh.ui.theme.MonehTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
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
        Surface(color = MaterialTheme.colors.background) {
            val navController = androidx.navigation.compose.rememberNavController()
            androidx.navigation.compose.NavHost(
                navController = navController,
                startDestination = "splash"
            ) {
                composable(
                    route = "splash"
                ) {
                    SplashScreen(navController)
                }
                composable(
                    route = "login"
                ) {
                    LoginScreen(navController)
                }
                composable(
                    route = "summary"
                ) {
                    SummaryScreen(summary = Summary.sampleAssets())
                }
            }
        }
    }
}
