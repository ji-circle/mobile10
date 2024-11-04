package com.example.fourthapp.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.fourthapp.R

@Composable
//리턴타입으로 String들의 Set...
fun LoadStringArrayToSet(): Set<String> {
    //strings.xml의 string array들을 불러오기
    val context = LocalContext.current

    //먼저 array를 만들고 불러오기
    val stringArray = context.resources.getStringArray(R.array.game_strings)

    //set으로 만들기
    val stringSet = stringArray.toSet()

    return stringSet
}