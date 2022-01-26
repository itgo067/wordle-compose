package repository

import models.Level
import kotlin.math.max

class LocalStorageLevelRepository : LevelRepository {

    private var lastLevel: Long = 0L

    override fun getCurrentLevelNumber(): Long {
        return lastLevel
    }

    override fun levelPassed(level: Level) {
        val settingLevel = max(level.number + 1, lastLevel)
        lastLevel = settingLevel
    }
}