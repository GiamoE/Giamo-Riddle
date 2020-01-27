package com.example.riddlemethis.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.riddlemethis.model.Riddle

@Database(entities = [Riddle::class], version = 3, exportSchema = false)
abstract class RiddlesRoomDatabase : RoomDatabase() {

    abstract fun riddleDao(): RiddleDao

    companion object {
        private const val DATABASE_NAME = "RIDDLES_DATABASE"

        @Volatile
        private var riddlesRoomDatabaseInstance: RiddlesRoomDatabase? = null

        fun getDatabase(context: Context): RiddlesRoomDatabase? {
            if (riddlesRoomDatabaseInstance == null) {
                synchronized(RiddlesRoomDatabase::class.java) {
                    if (riddlesRoomDatabaseInstance == null) {
                        riddlesRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            RiddlesRoomDatabase::class.java,
                            DATABASE_NAME
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return riddlesRoomDatabaseInstance
        }
    }
}