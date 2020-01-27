package com.example.riddlemethis.database

import androidx.room.*
import com.example.riddlemethis.model.Riddle

@Dao
interface RiddleDao {
    @Query("SELECT * FROM riddleTable")
    suspend fun getAllRiddles(): List<Riddle>

    @Insert
    suspend fun insertRiddle(riddle: Riddle)

    @Delete
    suspend fun deleteRiddle(riddle: Riddle)

    @Query("DELETE FROM riddleTable")
    suspend fun deleteAllRiddles()

    @Update
    suspend fun updateRiddle(riddle: Riddle)
}