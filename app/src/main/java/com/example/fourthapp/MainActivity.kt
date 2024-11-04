package com.example.fourthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fourthapp.ui.GamePage
import com.example.fourthapp.ui.ResultPage
import com.example.fourthapp.ui.theme.FourthAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FourthAppTheme {
//                GamePage()
                ResultPage()
            }
        }
    }
}
//words 등록 시 Project > src > main > res > values > string으로 접근
//"(.*?)", <item>$1</item> 이거로 (.* 누른 상태로) string에서 string-array로 관리

