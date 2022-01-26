package repository

import models.Level


interface LevelRepository {
    fun getCurrentLevelNumber(): Long

    fun levelPassed(level: Level)
}