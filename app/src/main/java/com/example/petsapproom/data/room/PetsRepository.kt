package com.example.petsapproom.data.room

import androidx.annotation.WorkerThread
import com.example.petsapproom.data.cursor.PetOpenHelper
import kotlinx.coroutines.flow.Flow

class PetsRepository(private val petsDao: PetsDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val petsList: Flow<List<EntityPet>> = petsDao.getAllPets()


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(entityPet: EntityPet){
        petsDao.insertPet(entityPet)
    }
    @Suppress
    @WorkerThread
    suspend fun update(entityPet: EntityPet){
        petsDao.updatePet(entityPet)
    }
}