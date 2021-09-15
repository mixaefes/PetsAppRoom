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
    private val prefs: SharedPreferences,
    private val context: Context
) : ViewModel() {
    private val mScope = CoroutineScope(Dispatchers.Main)

     val myPetDbHelper = PetOpenHelper(context)
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    // the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    var allPets: LiveData<List<EntityPet>> = repository.petsList.asLiveData()
  //  var allPetsCursor = myPetDbHelper.getAllPets()
    fun getAll():LiveData<List<EntityPet>>{

      return when (prefs.getBoolean("switch_to_cursor", false)) {
              true -> myPetDbHelper.getAllPets().asLiveData()
              else -> repository.petsList.asLiveData()
          }
    }

    //Launching a new coroutine to insert the data in a non-blocking way
/*    fun insertPet(entityPet: EntityPet) = viewModelScope.launch {
        repository.insert(entityPet)
    }*/
    fun insertPet(entityPet: EntityPet) {
        if (prefs.getBoolean("switch_to_cursor", false)) {
            Log.i("ViewModel", "switch cursor is ${prefs.getBoolean("switch_to_cursor", false)}")
        //    val petDbHelper = PetOpenHelper(context)
            myPetDbHelper.insertPet(entityPet)
        } else {
            viewModelScope.launch { repository.insert(entityPet) }
        }
    }

    fun updatePet(entityPet: EntityPet) {
        viewModelScope.launch { repository.update(entityPet) }
        when(prefs.getBoolean("switch_to_cursor", false)){
            true -> myPetDbHelper.updatePet(entityPet)
            false ->viewModelScope.launch { repository.update(entityPet)}
        }
    }
    fun deletePet(entityPet: EntityPet) {
        when(prefs.getBoolean("switch_to_cursor", false)){
            true -> myPetDbHelper.deletePet(entityPet)
            false ->viewModelScope.launch { repository.deletePet(entityPet) }
        }

    }

    fun getPet(id: Int): LiveData<EntityPet?> {
        Log.i(TAG,"getPet")
        return when (prefs.getBoolean("switch_to_cursor", false)) {
            true -> myPetDbHelper.getPetById(id)
            else -> repository.getPetById(id)
        }
    }
    companion object{
        private const val TAG = "PetsViewModel"
    }
}

class PetViewModelFactory(
    private val repository: PetsRepository,
    private val prefs: SharedPreferences,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetsViewModel(repository, prefs, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}