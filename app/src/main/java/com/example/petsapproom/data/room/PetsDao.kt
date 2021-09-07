package com.example.petsapproom.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PetsDao {
    @Query("SELECT * FROM pets")
    fun getAllPets(): Flow<List<EntityPet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPet(entityPet: EntityPet)

    @Query("DELETE FROM pets")
   suspend fun deleteAllPets()
}