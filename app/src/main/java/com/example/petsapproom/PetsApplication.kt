package com.example.petsapproom

import android.app.Application
import com.example.petsapproom.data.PetsRepository
import com.example.petsapproom.data.PetsRoomDatabase

class PetsApplication : Application(){
    val database by lazy { PetsRoomDatabase.getDatabase(this) }
    val repository by lazy { PetsRepository(database.petsDao()) }
}