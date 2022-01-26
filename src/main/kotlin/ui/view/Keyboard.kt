package ui.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.EqualityStatus
import models.KeyboardKeys
import ui.theme.*
import viewmodel.GameViewModel

@Composable
internal fun GameKeyboard(
    state: GameViewModel.State,
    onKey: (key: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit
) {

    BoxWithConstraints {
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                repeat(10) {
                    val key = state.game.availableKeyboard.keys[it]
                    KeyboardKey(key, onKey, Modifier.weight(1f))
                }
            }

            Spacer(Modifier.size(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(start = 8.dp)) {
                repeat(9) {
                    val key = state.game.availableKeyboard.keys[10 + it]
                    KeyboardKey(key, onKey, Modifier.weight(1f))
                }
            }

            Spacer(Modifier.size(4.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier) {
                KeyboardKey(text = " ENTER ", onClick = onSubmit)
                repeat(7) {
                    val key = state.game.availableKeyboard.keys[19 + it]
                    KeyboardKey(key, onKey, Modifier.weight(1f))
                }

                KeyboardKey(text = "⌫", onClick = onBackspace, modifier = Modifier.width(64.dp))
            }
        }
    }

}

@Composable
private fun KeyboardKey(key: KeyboardKeys.Key, onKey: (key: Char) -> Unit, modifier: Modifier = Modifier) {
    KeyboardKey(key.button.toString().uppercase(), modifier = modifier, key.equalityStatus) {
        onKey(key.button)
    }
}


@Composable
private fun KeyboardKey(
    text: String, modifier: Modifier = Modifier, status: EqualityStatus? = null, onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = when (status) {
            EqualityStatus.Incorrect -> MaterialTheme.colors.keyboardDisabled
            else -> MaterialTheme.colors.keyboard
        }
    )
    val testColor by animateColorAsState(
        targetValue = when (status) {
            EqualityStatus.WrongPosition -> MaterialTheme.colors.wrongPositionBackground
            EqualityStatus.Correct -> MaterialTheme.colors.correctBackground
            EqualityStatus.Incorrect, null -> MaterialTheme.colors.onKeyboard
        }
    )
    Box(
        modifier.height(40.dp).clip(RoundedCornerShape(2.dp)).background(color).clickable(onClick = onClick),
        Alignment.Center
    ) {
        Text(modifier = Modifier, text = text, color = testColor, fontSize = 18.sp)
    }
}