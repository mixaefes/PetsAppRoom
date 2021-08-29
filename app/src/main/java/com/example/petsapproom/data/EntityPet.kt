package com.example.petsapproom.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class EntityPet(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var breed: String,
    var gender: Int,
    var weight: Int
)