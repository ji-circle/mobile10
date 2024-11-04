package com.example.fourthapp.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.fourthapp.R

@Composable
fun LoadStringArrayToSet(): Set<String> {
    val context = LocalContext.current

    val stringArray = context.resources.getStringArray(R.array.game_strings)

    val stringSet = stringArray.toSet()

    return stringSet
}