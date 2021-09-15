package com.example.petsapproom.model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.petsapproom.PetsApplication
import com.example.petsapproom.data.cursor.PetOpenHelper
import com.example.petsapproom.data.room.EntityPet
import com.example.petsapproom.data.room.PetsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PetsViewModel(
    private val repository: PetsRepository,
) : ViewModel() {


  /*  fun getAll(): LiveData<List<EntityPet>> {
        return repository.petsList.asLiveData()
    }*/
   fun getAll(): LiveData<List<EntityPet>> {
        return repository.getAllPets().asLiveData()
    }


    fun insertPet(entityPet: EntityPet) {
        viewModelScope.launch { repository.insert(entityPet) }
    }

    fun updatePet(entityPet: EntityPet) {
        viewModelScope.launch { repository.update(entityPet) }
    }

    fun deletePet(entityPet: EntityPet) {
        viewModelScope.launch { repository.deletePet(entityPet) }
    }

    fun getPet(id: Int): LiveData<EntityPet?> {
        Log.i(TAG, "getPet")
        return repository.getPetById(id)
    }

    companion object {
        private const val TAG = "PetsViewModel"
    }

}

class PetViewModelFactory(
    private val repository: PetsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}