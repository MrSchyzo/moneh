package com.example.moneh.ui.utils

infix fun <I, O1, O2> ((I) -> O1).andThen(other: (O1) -> O2): (I) -> O2 =
    {other(this(it))}
