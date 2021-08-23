package com.example.petsapproom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityPet::class], version = 1, exportSchema = false)
abstract class PetsRoomDatabase : RoomDatabase() {
    abstract fun petsDao(): PetsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        private var INSTANCE: PetsRoomDatabase? = null
        private fun getDatabase(context: Context): PetsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PetsRoomDatabase::class.java,
                    "petsDatabase"
                ).build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}