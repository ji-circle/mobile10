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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                            .fillMaxSize()
                            .padding(innerPadding)
                            //아래 코드를 통해, 애초에 이 안의 것들의 가로 패딩을 주게됨..
                            //  그럼 아래에서 각각에 fillMaxWidth() 했을 때에도 적용됨!
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        //이 밑의 코드로 카드와 버튼들의 정렬이 가운데정렬이 됨
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier
                                        .clip(shapes.medium)
                                        .background(color = colorScheme.surfaceTint)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                        .align(alignment = Alignment.End),
                                    text = "0/10",
                                    style = typography.titleMedium,
                                    color = colorScheme.onPrimary
                                )

                                Text(
                                    text = "Vocabulary",
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
                                    colors = TextFieldDefaults.colors(
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
                        Column(
                            //vertical 패딩을 줘서 각각의 세로 간격을 줌
                            modifier = Modifier.padding(vertical = 32.dp),
                            //아래 코드로 버튼들 간의 간격을 추가함
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {}
                            ) {
                                Text(text = "Submit")
                            }
                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {}
                            ) {
                                Text(text = "Skip")
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