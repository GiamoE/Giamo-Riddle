package com.example.riddlemethis.database

import android.content.Context
import com.example.riddlemethis.model.Riddle

class RiddleRepository(context: Context) {

    private val riddleDao: RiddleDao

    init {
        val database = RiddlesRoomDatabase.getDatabase(context)
        riddleDao = database!!.riddleDao()
    }

    suspend fun getAllRiddles(): List<Riddle> {
        return riddleDao.getAllRiddles()
    }

    suspend fun insertRiddle(riddle: Riddle) {
        riddleDao.insertRiddle(riddle)
    }

    suspend fun deleteRiddle(riddle: Riddle) {
        riddleDao.deleteRiddle(riddle)
    }

    suspend fun deleteAllRidles() {
        riddleDao.deleteAllRiddles()
    }

    suspend fun updateRiddle(riddle: Riddle) {
        riddleDao.updateRiddle(riddle)
    }
}