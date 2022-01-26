package ui.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Level
import java.awt.Desktop
import java.net.URI


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ColumnScope.GameHeader(level: Level) {
    Column(
        Modifier
            .clickable {
                Desktop.getDesktop().browse(URI.create("https://github.com/itgogogogo/wordle-compose"))
            }
            .align(Alignment.CenterHorizontally)) {

        var revealing by remember(level) { mutableStateOf(false) }

        Text(
            text = "Wordle Compose",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
        )

        Text(
            text = "github.com/itgogogogo/wordle-compose",
            style = MaterialTheme.typography.body2,
            fontSize = 10.sp,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        )
        Row(
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Level ${level.number}",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Serif,
            )
            AnimatedContent(revealing, modifier = Modifier.padding(start = 16.dp)) {
                if (!it) {
                    Text(text = "(reveal)",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.clickable {
                            revealing = true
                        })
                } else {
                    Text(text = level.word.word, style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.primary,

                        modifier = Modifier.clickable {
                            revealing = false
                        })
                }
            }

        }

    }
}