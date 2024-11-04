package com.example.fourthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fourthapp.ui.theme.FourthAppTheme
import com.example.fourthapp.ui.theme.Typography

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FourthAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text(text = "Unscrambling Game") })
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            //fillMaxSize 써야 이 컬럼, verticalArrangement의 범위 정해짐
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            //카드 하단에 그림자 주기
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            //카드 내에 컬럼으로 싸기
                            Column(
                                modifier = Modifier
                                    //center horizontally 쓰려면 fillmaxwidth 해주기
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                //안에 든 것들끼리의 사이 패딩 줄 때 이래도 되는듯
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    //해당 텍스트의 백그라운드 넣기
                                    modifier = Modifier
                                        .background(color = colorScheme.surfaceTint)
                                        //여기에 패딩을 주면 0/10 의 백그라운드의 패딩이 늘어남
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                        //이것만 중앙정렬??이 아니라 우측에 넣으려면 여기에 align(End)
                                        .align(alignment = Alignment.End),
                                    text = "0/10",
                                    style = typography.titleMedium,
                                    //글자의 색깔 변경
                                    color = colorScheme.onPrimary
                                )

                                Text(
                                    text = "Vocabulary",
                                    //크기 조정...
                                    style = typography.displayMedium
                                )
                                Text(
                                    text = "Unscramble the above vocabulary!",
                                    style = typography.titleMedium
                                )
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = "",
                                    onValueChange = {},
                                    //컬러 바꾸기
                                    colors = TextFieldDefaults.colors(
                                        //ui.theme에서 Theme.kt 들어가기

                                        //focusedContainerColor = colorScheme.surface 이건 흰색을 뜻함
                                        focusedContainerColor = colorScheme.surface,
                                        unfocusedContainerColor = colorScheme.surface,
                                        disabledContainerColor = colorScheme.surface

                                    ),
                                    label = {
                                        Text(text = "Enter your word")
                                    }
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FourthAppTheme {
        Greeting("Android")
    }
}