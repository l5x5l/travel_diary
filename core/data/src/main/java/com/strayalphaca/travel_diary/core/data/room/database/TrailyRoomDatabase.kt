package com.strayalphaca.travel_diary.core.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.core.data.room.entity.FileEntity
import com.strayalphaca.travel_diary.core.data.room.entity.LocationEntity
import com.strayalphaca.travel_diary.core.data.room.entity.RecordEntity
import com.strayalphaca.travel_diary.core.data.room.entity.RecordFileEntity

@Database(
    entities = [RecordEntity::class, FileEntity::class, RecordFileEntity::class, LocationEntity::class],
    version = 1
)
abstract class TrailyRoomDatabase : RoomDatabase() {
    abstract fun recordDao() : RecordDao

    companion object {
        private lateinit var instance : TrailyRoomDatabase

        fun getInstance(context : Context) : TrailyRoomDatabase {
            if (!Companion::instance.isInitialized) {
                synchronized(TrailyRoomDatabase::class) {
                    instance = Room.databaseBuilder(context, TrailyRoomDatabase::class.java, "traily-local").build()
                }
            }

            return instance
        }

        fun clearDatabase() {
            if (!Companion::instance.isInitialized) return
            instance.clearAllTables()
        }
    }
}