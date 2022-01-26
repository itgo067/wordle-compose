package repository

import models.EqualityStatus
import models.Word
import models.WordStatus

class GetWordStatus(private val wordRepository: WordRepository) {

    fun execute(word: Word, original: Word): WordStatus {
        return when {
            !wordRepository.find(word) -> WordStatus.NotExists
            word == original -> WordStatus.Correct
            else -> {
                val missedCharacters = original.word.mapIndexed { index, ch ->
                    if (word.word.getOrNull(index) != ch) ch else null
                }.filterNotNull().toMutableList()

                val status = word.word.mapIndexed { index, ch ->
                    val charAtOriginal = original.word.getOrNull(index) ?: -1
                    val originalIndex = original.word.indexOf(ch)
                    when {
                        ch == charAtOriginal -> EqualityStatus.Correct
                        originalIndex >= 0 && missedCharacters.remove(ch) -> EqualityStatus.WrongPosition
                        else -> EqualityStatus.Incorrect
                    }
                }
                WordStatus.Incorrect(status.toTypedArray())
            }
        }
    }
}