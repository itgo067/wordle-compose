package viewmodel

import models.Level
import repository.GetNextLevel
import repository.LevelRepository

class LevelsViewModel(private val levelRepository: LevelRepository, private val getNextLevel: GetNextLevel) :
    BaseViewModel<LevelsViewModel.State>(State()) {

    data class State(
        val currentLevel: Level? = null
    )

    init {
        updateLevel()
    }

    fun levelPassed() {
        currentState().currentLevel?.let { levelRepository.levelPassed(it) }
        updateLevel()
    }

    private fun updateLevel() {
        updateState {
            copy(currentLevel = getNextLevel.execute())
        }
    }
}