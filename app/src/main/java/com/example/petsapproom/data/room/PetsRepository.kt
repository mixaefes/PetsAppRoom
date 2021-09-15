package com.example.petsapproom.data.room

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import com.example.petsapproom.data.cursor.PetOpenHelper
import kotlinx.coroutines.flow.Flow

class PetsRepository(private val petsDao: PetsDao,
context: Context, cursorSource:PetOpenHelper) {

    private val cursorData = cursorSource
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val petsList: Flow<List<EntityPet>> = if(pref.getBoolean("switch_to_cursor", false))cursorData.getAllPets() else petsDao.getAllPets()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getAllPets(): Flow<List<EntityPet>>{
       return when(pref.getBoolean("switch_to_cursor", false)) {
            true -> cursorData.getAllPets()
           // else -> petsDao.getAllPets()
            else -> petsDao.getAllPetsSorted(pref.getString("sort_by","")!!)
        }
    }
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(entityPet: EntityPet){
        when(pref.getBoolean("switch_to_cursor", false)) {
            true -> cursorData.insertPet(entityPet)
           else -> petsDao.insertPet(entityPet)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePet(entityPet: EntityPet){

        when(pref.getBoolean("switch_to_cursor", false)) {
            true -> cursorData.deletePet(entityPet)
            else -> petsDao.deletePet(entityPet)
        }
    }
    @Suppress
    @WorkerThread
    suspend fun update(entityPet: EntityPet){

        when(pref.getBoolean("switch_to_cursor", false)) {
            true -> cursorData.updatePet(entityPet)
            else -> petsDao.updatePet(entityPet)
        }

    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getPetById(id: Int): LiveData<EntityPet?> {
        Log.i(TAG, "getPetById")
     return when(pref.getBoolean("switch_to_cursor", false)) {
            true -> cursorData.getPetById(id)
            else -> petsDao.getPetById(id)
        }
    }
    companion object{
        private const val TAG = "PetsRepository"
    }
}