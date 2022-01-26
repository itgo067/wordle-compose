package repository

import models.Level

class GetNextLevel(private val wordRepository: AssetFileWordRepository) {

    fun execute(): Level {
        val currentLevelNumber = 1L
        return wordRepository.getWordForLevel(currentLevelNumber).let { word -> Level(currentLevelNumber, word) }
    }
}