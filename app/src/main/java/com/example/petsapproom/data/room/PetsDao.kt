package com.example.petsapproom.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PetsDao {
    @Query("SELECT * FROM pets")
    fun getAllPets(): Flow<List<EntityPet>>

    @Query("SELECT * FROM pets ORDER BY " +
            "CASE WHEN :orderBy = 'name' THEN name END," +
            "CASE WHEN :orderBy = 'weight' THEN weight END," +
            "CASE WHEN :orderBy = 'breed' THEN breed END," +
            "CASE WHEN :orderBy = '' THEN id END")
    fun getAllPetsSorted(orderBy:String): Flow<List<EntityPet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPet(entityPet: EntityPet)

    @Query("DELETE FROM pets")
   suspend fun deleteAllPets()

   @Update
   suspend fun updatePet(entityPet: EntityPet)

   @Delete
   suspend fun deletePet(entityPet: EntityPet)

   @Query("SELECT * FROM pets WHERE id = :id")
   fun getPetById(id:Int): LiveData<EntityPet?>
}