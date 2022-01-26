package repository

import models.Word

interface WordRepository {

    fun find(word: Word): Boolean

    fun random(): Word

    fun getWordForLevel(currentLevelNumber:Long) : Word
}