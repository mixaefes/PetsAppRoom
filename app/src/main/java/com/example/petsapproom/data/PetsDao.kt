package com.example.petsapproom.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PetsDao {
    @Query("SELECT * FROM pets")
    fun getAllPets(): Flow<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPet(pet:Pet)

    @Query("DELETE FROM pets")
   suspend fun deleteAllPets()
}