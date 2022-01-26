package models

import models.KeyboardKeys.Key.Companion.englishKeys

enum class Language(val keys: List<Char>) {
    English(englishKeys)
}

abstract class KeyboardKeys(open val keys: List<Key>, val language: Language) {

    abstract fun withUpdateButton(keys: List<Key>): KeyboardKeys

    data class Key(val button: Char, val equalityStatus: EqualityStatus?) {

        val enabled = equalityStatus != EqualityStatus.Incorrect

        companion object {
            val englishKeys = listOf(
                'Q',
                'W',
                'E',
                'R',
                'T',
                'Y',
                'U',
                'I',
                'O',
                'P',
                'A',
                'S',
                'D',
                'F',
                'G',
                'H',
                'J',
                'K',
                'L',
                'Z',
                'X',
                'C',
                'V',
                'B',
                'N',
                'M'
            )
        }
    }

    data class English(
        override val keys: List<Key> = englishKeys.map { Key(it, null) }.toList()
    ) : KeyboardKeys(keys, Language.English) {
        override fun withUpdateButton(keys: List<Key>): KeyboardKeys {
            return copy(keys = keys)
        }
    }
}