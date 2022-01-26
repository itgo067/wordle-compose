package ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import viewmodel.GameViewModel

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun BoxScope.ErrorScreen(
    state: GameViewModel.State,
    shownError: () -> Unit,
) {
    LaunchedEffect(key1 = state.doesNotExist, block = {
        if (state.doesNotExist) {
            delay(2000)
            shownError()
        }
    })
    AnimatedVisibility(
        state.doesNotExist, modifier = Modifier
            .align(Alignment.BottomCenter)
    ) {
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colors.error)
                .padding(16.dp)
        ) {
            Text(
                text = "The word does not exist!",
                color = MaterialTheme.colors.onError
            )
        }
    }
}
