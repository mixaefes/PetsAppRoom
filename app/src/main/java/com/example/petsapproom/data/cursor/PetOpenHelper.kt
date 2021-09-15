package com.example.petsapproom.data.cursor

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.petsapproom.Constants
import com.example.petsapproom.data.room.EntityPet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf


class PetOpenHelper(context: Context) : SQLiteOpenHelper(
    context,
    Constants.DATABASE_NAME,
    null,
    Constants.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Constants.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(Constants.SQL_DELETE_ENTRIES)
        onCreate(db)
    }


    private fun getCursor(): Cursor {
        return this.readableDatabase.rawQuery("SELECT * FROM ${Constants.TABLE_PETS_NAME};", null)
    }

    fun insertPet(entityPet: EntityPet): Long {
        Log.i(TAG, "insert new pet by cursor is executed")

        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.apply {
            put(Constants.COLUMN_NAME, entityPet.name)
            put(Constants.COLUMN_BREED, entityPet.breed)
            put(Constants.COLUMN_GENDER, entityPet.gender)
            put(Constants.COLUMN_WEIGHT, entityPet.weight)
        }
        return db.insert(Constants.TABLE_PETS_NAME, null, values)
    }

    fun updatePet(entityPet: EntityPet) {
        Log.i(TAG,"update by cursor is execute")
        val values = ContentValues()
        values.apply {
            put(Constants.COLUMN_NAME, entityPet.name)
            put(Constants.COLUMN_BREED, entityPet.breed)
            put(Constants.COLUMN_GENDER, entityPet.gender)
            put(Constants.COLUMN_WEIGHT, entityPet.weight)
        }
        this.writableDatabase.update(
            Constants.TABLE_PETS_NAME,
            values,
            "id" + "=?",
            arrayOf(entityPet.id.toString())
        )
    }

    fun getAllPets(): Flow<List<EntityPet>> {
        Log.i(TAG, "getAllPets is executed")
        val cursor =
            this.readableDatabase.rawQuery("SELECT * FROM ${Constants.TABLE_PETS_NAME};", null)
        val listOfPets = mutableListOf<EntityPet>()
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val petId = it.getInt(it.getColumnIndex(it.getColumnName(0)))
                    val petName = it.getString(it.getColumnIndex(Constants.COLUMN_NAME))
                    val petBreed = it.getString(it.getColumnIndex(Constants.COLUMN_BREED))
                    val petGender = it.getInt(it.getColumnIndex(Constants.COLUMN_GENDER))
                    val petWeight = it.getInt(it.getColumnIndex(Constants.COLUMN_WEIGHT))
                    val pet = EntityPet(
                        name = petName,
                        breed = petBreed,
                        gender = petGender,
                        weight = petWeight,
                        id = petId
                    )
                    listOfPets.add(pet)
                } while (it.moveToNext())
            }
        }
        cursor.close()
        return flowOf(listOfPets)
    }

    fun getPetById(id: Int): LiveData<EntityPet?> {
        Log.i(TAG, "getPetById by cursor is called id=$id")
        val petById = MutableLiveData<EntityPet?>()
        val selectionQuery = "SELECT * FROM ${Constants.TABLE_PETS_NAME} WHERE id = $id"

        val cursor = this.writableDatabase.rawQuery(selectionQuery, null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME))
                    val breed = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_BREED))
                    val gender = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_GENDER))
                    val weight = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_WEIGHT))
                    petById.value =
                        (EntityPet(
                            name = name,
                            breed = breed,
                            gender = gender,
                            weight = weight,
                            id = id
                        ))
                } while (it.moveToNext())
            }
        }
        Log.i(TAG, "this is petById: ${petById.value}")
        cursor.close()
        return petById
    }

    fun deletePet(entityPet: EntityPet) {
        Log.i(TAG, "delete pet by cursor")
        this.writableDatabase.delete(
            Constants.TABLE_PETS_NAME,
            "id" + "=?",
            arrayOf(entityPet.id.toString())
        )
    }

    companion object {
        private const val TAG = "PetOpenHelperClass"
    }
}