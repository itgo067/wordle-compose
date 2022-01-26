package repository

import androidx.compose.ui.res.useResource
import models.Word

class AssetFileWordRepository : WordRepository {

    private val allWords = useResource("words.txt", block = { it ->
        it.readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }
            .shuffled().toSet()
    })

    private val choosingWords = useResource("top.txt", block = {
        it.readBytes().decodeToString().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }
            .shuffled().toList()
    })

    override fun find(word: Word): Boolean {
        return choosingWords.contains(word.word.uppercase())
    }

    override fun random(): Word {
        return Word(allWords.random())
    }

    override fun getWordForLevel(currentLevelNumber: Long): Word {
        return Word(choosingWords[currentLevelNumber.toInt() - 1])
    }
}