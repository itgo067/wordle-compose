package ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import viewmodel.GameViewModel

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun BoxScope.GameOverScreen(
    state: GameViewModel.State,
    shownLost: () -> Unit,
) {

    AnimatedVisibility(
        state.game.isOver, modifier = Modifier.align(Alignment.Center)
    ) {
        Box(Modifier
            .align(Alignment.BottomCenter)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colors.primary)
            .clickable {
                shownLost()
            }
            .padding(84.dp), Alignment.Center) {
            Text(
                text = "You lose. Tap to retry.",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}