package com.example.petsapproom.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class EntityPet(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val breed: String,
    val gender: Int,
    val weight: Int
)