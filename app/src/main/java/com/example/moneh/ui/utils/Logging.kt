package com.example.moneh.ui.utils

import android.util.Log

fun logError(incipit: String): (Any?) -> Unit = {Log.e(null, "$incipit: $it")}
