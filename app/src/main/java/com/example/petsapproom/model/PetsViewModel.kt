package com.example.petsapproom.model

import androidx.lifecycle.*
import com.example.petsapproom.data.room.EntityPet
import com.example.petsapproom.data.room.PetsRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PetsViewModel(private val repository: PetsRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    // the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPets: LiveData<List<EntityPet>> = repository.petsList.asLiveData()

    //Launching a new coroutine to insert the data in a non-blocking way
    fun insertPet(entityPet: EntityPet) = viewModelScope.launch {
        repository.insert(entityPet)
    }

}

class PetViewModelFactory(private val repository: PetsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}