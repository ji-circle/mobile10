package com.example.fourthapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fourthapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultPage(
    gameViewModel: GameViewModel = viewModel(),
    returnToGame: () -> Unit,
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.game_result)) },
                navigationIcon = {
                    IconButton(onClick = returnToGame) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
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
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.your_score_is),
                        style = typography.titleLarge
                    )
                    Text(
                        text = gameUiState.score.toString(),
                        style = typography.displayMedium
                    )

                    //여기 추가
                    if (gameUiState.isHighlightExists) {
                        Text(
                            text = stringResource(id = R.string.your_highlight_word_is),
                            style = typography.titleLarge
                        )

                        //여기 추가
                        val highlightIterator = gameViewModel.highlightWords.iterator()
                        val shuffledIterator = gameViewModel.highlightShuffled.iterator()

                        while (highlightIterator.hasNext() && shuffledIterator.hasNext()) {
                            Text(
                                text = shuffledIterator.next(),
                                style = typography.displayMedium
                            )
                            Text(
                                text = " to ",
                                style = typography.displaySmall
                            )
                            Text(
                                text = highlightIterator.next(),
                                style = typography.displayMedium
                            )
                            if (highlightIterator.hasNext()) {
                                Text(
                                    text = "\n"
                                )
                            }
                        }
                    }
                }
            }
            //여기로 이동
            Column(
                modifier = Modifier.padding(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        gameViewModel.resetGame()
                        returnToGame()
                    }
                ) {
                    Text(text = "Return to Game")
                }
            }
        }
    }
}
