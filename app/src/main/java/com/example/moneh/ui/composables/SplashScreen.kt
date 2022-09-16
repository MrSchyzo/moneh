package com.example.moneh.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        delay(3000)
        navController.navigate("login") {
            popUpTo("splash") {
                inclusive = true
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .size(128.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
        )
    }
}
