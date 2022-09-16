package com.example.moneh.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Button(onClick = {
            navController.navigate("summary") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }) {
            Text(text = "Log in")
        }
    }
}
