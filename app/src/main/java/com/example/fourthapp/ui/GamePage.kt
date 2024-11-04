package com.example.fourthapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fourthapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePage(
    gameViewModel: GameViewModel = viewModel(),
    //아래 추가
    checkScore: () -> Unit
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GameLayout(
                onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
                isGuessWrong = gameUiState.isGuessedWordWrong,
                wordCount = gameUiState.currentWordCount,
                userGuess = gameViewModel.userGuess,
                onKeyboardDone = { gameViewModel.checkUserGuess() },
                currentScrambledWord = gameUiState.currentScrambledWord,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.padding(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { gameViewModel.checkUserGuess() }
                ) {
                    Text(text = "Submit")
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { gameViewModel.skipWord() }
                ) {
                    Text(text = "Skip")
                }

            }
        }
        //finalDialog에서 체크를 누르는 순간 넘어가는 것을
        //  여기서 구현하는 게 아니고, mainActivity에서 구현함! (navcontroller 부분)

        if (gameUiState.isGameOver){
            FinalDialog(checkScore = checkScore)

        }
    }
}

@Composable
fun GameLayout(
    onUserGuessChanged: (String) -> Unit,
    isGuessWrong: Boolean,
    wordCount: Int,
    userGuess: String,
    onKeyboardDone: () -> Unit,
    currentScrambledWord: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
                text = stringResource(id = R.string.current_score, wordCount),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )

            Text(
                text = currentScrambledWord,
                style = typography.displayMedium
            )
            Text(
                text = "Unscramble the above vocabulary!",
                style = typography.titleMedium
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userGuess,
                onValueChange = onUserGuessChanged,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                    errorContainerColor = colorScheme.surface

                ),
                label = {
                    if (isGuessWrong) {
                        Text(text = stringResource(id = R.string.wrong_guess))
                    } else {
                        Text(text = stringResource(id = R.string.enter_your_word))
                    }

                },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }

}

//아래 추가
@Composable
private fun FinalDialog(
    checkScore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(id = R.string.alert_title)) },
        text = { Text(text = stringResource(id = R.string.alert_content)) },
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = checkScore) {
                Text(text = stringResource(id = R.string.check_score))
            }
        }
    )
}