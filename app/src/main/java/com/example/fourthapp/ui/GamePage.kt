package com.example.fourthapp.ui

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    checkScore: () -> Unit,
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    //아래 추가
    val openHighlightDialog = remember { mutableStateOf(false) }

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
        if (gameUiState.isGameOver) {
            FinalDialog(checkScore = checkScore)

        }

        if (gameUiState.isMoreThan7) {

            when {
                openHighlightDialog.value -> {
                    HighlightDialog(
                        onDismissRequest = { openHighlightDialog.value = false },
//                        onDismissRequest = { },
                        onConfirmation = {
                            openHighlightDialog.value = false


                            gameViewModel.addHighlightWords()
                            Log.d("언제되는거지?", "${gameViewModel.highlightWithShuffled}")
                            Log.d("${gameViewModel.highlightWithShuffled}", "언제되는거지...")
                        },
                        dialogTitle = stringResource(id = R.string.highlight_title),
                        dialogText = stringResource(id = R.string.highlight_content)

                    )
                }
            }
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

@Composable
private fun HighlightDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },

        modifier = modifier,
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(id = R.string.highlight_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(id = R.string.highlight_dismiss))
            }
        }
    )

}