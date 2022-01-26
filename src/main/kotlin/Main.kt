// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import repository.AssetFileWordRepository
import repository.GetNextLevel
import repository.GetWordStatus
import repository.LocalStorageLevelRepository
import ui.view.WordScreen
import viewmodel.LevelsViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Wordle Compose",
        state = rememberWindowState(width = 400.dp, height = 660.dp),
        resizable = false
    ) {
        MaterialTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

                val assetWordRepository = remember { AssetFileWordRepository() }

                val getWordStatus = remember { GetWordStatus(assetWordRepository) }

                val levelRepository = remember { LocalStorageLevelRepository() }

                val getNextLevel = remember { GetNextLevel(wordRepository = assetWordRepository) }

                val levelViewModel = remember { LevelsViewModel(levelRepository, getNextLevel) }

                val level = levelViewModel.state().collectAsState().value.currentLevel
                if (level != null) {
                    WordScreen(level, getWordStatus) {
                        levelViewModel.levelPassed()
                    }
                }
            }
        }
    }
}



