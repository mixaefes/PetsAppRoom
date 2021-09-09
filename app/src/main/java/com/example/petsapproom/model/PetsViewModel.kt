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
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PetsViewModel(private val repository: PetsRepository,
private val prefs: SharedPreferences,
private val context: Context) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    // the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val allPets: LiveData<List<EntityPet>> = repository.petsList.asLiveData()

    //Launching a new coroutine to insert the data in a non-blocking way
/*    fun insertPet(entityPet: EntityPet) = viewModelScope.launch {
        repository.insert(entityPet)
    }*/
    fun insertPet(entityPet: EntityPet){
        if(prefs.getBoolean("switch_to_cursor",false)){
            Log.i("ViewModel","switch cursor is ${prefs.getBoolean("switch_to_cursor",false)}" )
            val petDbHelper = PetOpenHelper(context)
            petDbHelper.insertPet(entityPet)
        }else{
            viewModelScope.launch { repository.insert(entityPet) }
        }
    }
    fun updatePet(entityPet: EntityPet){
        viewModelScope.launch { repository.update(entityPet) }
    }

}

class PetViewModelFactory(private val repository: PetsRepository,
private val prefs:SharedPreferences,
private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetsViewModel(repository,prefs,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}