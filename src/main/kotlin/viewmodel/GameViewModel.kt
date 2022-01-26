package viewmodel

import models.Game
import models.Guess
import models.Word
import models.WordStatus
import repository.GetWordStatus

class GameViewModel(private val initialGame: Game, private val getWordStatus: GetWordStatus) :
    BaseViewModel<GameViewModel.State>(State(initialGame)) {

    data class State(val game: Game, val currentlyEnteringWord: String? = null, val doesNotExist: Boolean = false)

    fun characterEntered(ch: Char) {
        if (wordIsEnteredCompletely()) return
        val character = ch.uppercaseChar()
        updateState { copy(currentlyEnteringWord = (currentlyEnteringWord ?: "") + character) }
    }

    private fun wordIsEnteredCompletely() =
        currentState().currentlyEnteringWord?.length == currentState().game.wordLength

    fun backspacePressed() {
        updateState {
            val newWord = when {
                currentlyEnteringWord == null -> null
                currentlyEnteringWord.length == 1 -> null
                else -> currentlyEnteringWord.dropLast(1)
            }
            copy(currentlyEnteringWord = newWord)
        }
    }

    fun submit() {
        if (!wordIsEnteredCompletely()) return
        val word = Word(currentState().currentlyEnteringWord!!)
        val status = getWordStatus.execute(word, currentState().game.originalWord)
        updateState {
            val newGuesses = if (status != WordStatus.NotExists) game.guesses + Guess(word, status) else game.guesses
            copy(
                game = game.copy(guesses = newGuesses),
                currentlyEnteringWord = if (status != WordStatus.NotExists) null else currentlyEnteringWord,
                doesNotExist = if (status == WordStatus.NotExists) true else doesNotExist
            )
        }
    }

    fun shownNotExist() {
        updateState { copy(doesNotExist = false) }
    }

    fun shownLost() {
        updateState {
            copy(game = game.copy(guesses = listOf()), currentlyEnteringWord = null, doesNotExist = false)
        }
    }
}