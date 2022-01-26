package ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.EqualityStatus
import models.WordStatus
import ui.theme.correctBackground
import ui.theme.enteringBackground
import ui.theme.incorrectBackground
import ui.theme.wrongPositionBackground
import viewmodel.GameViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameGrid(state: GameViewModel.State, modifier: Modifier = Modifier) {

    BoxWithConstraints(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(6) { row ->
                Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    repeat(5) { column ->
                        val character: Char?
                        val status: EqualityStatus?
                        if (row < state.game.guesses.size) {
                            val guess = state.game.guesses[row]
                            character = guess.word.word[column]
                            status = when (guess.wordStatus) {
                                WordStatus.Correct -> EqualityStatus.Correct
                                is WordStatus.Incorrect -> guess.wordStatus.equalityStatuses[column]
                                WordStatus.NotExists -> EqualityStatus.Incorrect
                            }
                        } else {
                            character =
                                if (row == state.game.guesses.size) state.currentlyEnteringWord?.getOrNull(column) else null
                            status = null
                        }

                        WordCharacterBox(character = character, status = status, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }

//    LazyVerticalGrid(
//        cells = GridCells.Fixed(state.game.wordLength),
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = modifier
//    ) {
//        val guesses = state.game.guesses.size
//        items((state.game.wordLength * guesses)) {
//            val guess = state.game.guesses[it / state.game.wordLength]
//            val position = it % state.game.wordLength
//            val character = guess.word.word[position]
//            val status = when (guess.wordStatus) {
//                WordStatus.Correct -> EqualityStatus.Correct
//                is WordStatus.Incorrect -> guess.wordStatus.equalityStatuses[position]
//                WordStatus.NotExists -> TODO()
//            }
//            WordCharacterBox(character = character, status = status)
//        }
//        if (guesses < 6) {
//            items(state.game.wordLength) {
//                val enteredChar = state.currentlyEnteringWord?.getOrNull(it)
//                if (enteredChar != null) {
//                    WordCharacterBox(character = enteredChar, status = null)
//                } else {
//                    EmptyCharacterBox()
//                }
//            }
//
//            val emptyBoxes = (6 - guesses - 1).coerceAtLeast(0)
//            items(emptyBoxes * state.game.wordLength) {
//                EmptyCharacterBox()
//            }
//        }
//    }
}

@Composable
private fun WordCharacterBox(character: Char?, status: EqualityStatus?, modifier: Modifier = Modifier) {
    val color = when (status) {
        EqualityStatus.WrongPosition -> MaterialTheme.colors.wrongPositionBackground
        EqualityStatus.Correct -> MaterialTheme.colors.correctBackground
        EqualityStatus.Incorrect -> MaterialTheme.colors.incorrectBackground
        null -> MaterialTheme.colors.enteringBackground
    }

    val textColor = when (status) {
        null -> MaterialTheme.colors.onBackground
        else -> MaterialTheme.colors.onPrimary
    }

    val borderModifier = if (status == null) Modifier.border(1.dp, MaterialTheme.colors.incorrectBackground) else Modifier
    BasicCharacterBox(borderModifier, color, character, textColor, modifier)
}

@Composable
private fun EmptyCharacterBox(modifier: Modifier = Modifier) {
    BasicCharacterBox(
        modifier = modifier,
        borderModifier = Modifier.border(1.dp, MaterialTheme.colors.incorrectBackground),
        color = Color.Transparent,
        character = null,
        textColor = Color.Transparent
    )
}

@Composable
private fun BasicCharacterBox(
    borderModifier: Modifier,
    color: Color,
    character: Char?,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    var lastChar by remember { mutableStateOf<Char?>(null) }
    if (character != null) {
        lastChar = character
    }
    Box(
        modifier.aspectRatio(1f).clip(RoundedCornerShape(2.dp)).then(borderModifier)
            .background(animateColorAsState(targetValue = color).value),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(character != null) {
            Text(
                lastChar?.uppercase() ?: "",
                color = animateColorAsState(targetValue = textColor).value,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Black)
            )
        }

    }
}

@Preview
@Composable
fun CharacterBoxPreview() {
    Row {
//        WordCharacterBox(character = 'A', status = null)
        WordCharacterBox(character = 'B', status = EqualityStatus.Incorrect)
//        WordCharacterBox(character = 'C', status = EqualityStatus.WrongPosition)
//        WordCharacterBox(character = 'D', status = EqualityStatus.Correct)
    }
}