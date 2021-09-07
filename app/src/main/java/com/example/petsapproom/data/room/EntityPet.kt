package com.example.petsapproom.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.petsapproom.Constants

@Entity(tableName = Constants.TABLE_PETS_NAME)
data class EntityPet(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var breed: String,
    var gender: Int,
    var weight: Int
)