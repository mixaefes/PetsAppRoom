package com.example.petsapproom

import android.app.Application
import com.example.petsapproom.data.cursor.PetOpenHelper
import com.example.petsapproom.data.room.PetsRepository
import com.example.petsapproom.data.room.PetsRoomDatabase

class PetsApplication : Application(){
    val database by lazy { PetsRoomDatabase.getDatabase(this) }
    val repository by lazy { PetsRepository(database.petsDao(),this, PetOpenHelper(this)) }
}