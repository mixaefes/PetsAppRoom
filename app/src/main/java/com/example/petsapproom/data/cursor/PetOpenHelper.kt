package com.example.petsapproom.data.cursor

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.petsapproom.Constants
import com.example.petsapproom.data.room.EntityPet


class PetOpenHelper(context: Context) :SQLiteOpenHelper(
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
        return readableDatabase.rawQuery("SELECT * FROM ${Constants.TABLE_PETS_NAME};", null)
    }
    fun insertPet(entityPet:EntityPet):Long{
        Log.i(TAG,"insert new pet by cursor is executed")

        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.apply {
            put(Constants.COLUMN_NAME,entityPet.name)
            put(Constants.COLUMN_BREED,entityPet.breed)
            put(Constants.COLUMN_GENDER,entityPet.gender)
            put(Constants.COLUMN_WEIGHT,entityPet.weight)
        }
        return db.insert(Constants.TABLE_PETS_NAME,null,values)
    }
    fun getAllPets():List<EntityPet>{
        Log.i(TAG,"getAllPets is executed")
        val cursor = getCursor()
        val listOfPets = mutableListOf<EntityPet>()
        cursor.use{
            if(it.moveToFirst()){
                do{
                    val petName = it.getString(it.getColumnIndex(Constants.COLUMN_NAME))
                    val petBreed = it.getString(it.getColumnIndex(Constants.COLUMN_BREED))
                    val petGender = it.getInt(it.getColumnIndex(Constants.COLUMN_GENDER))
                    val petWeight = it.getInt(it.getColumnIndex(Constants.COLUMN_WEIGHT))
                    val pet = EntityPet(name=petName,breed = petBreed,gender = petGender,weight = petWeight)
                    listOfPets.add(pet)
                }while(it.moveToNext())
            }
        }
        cursor.close()
        return listOfPets
    }
    companion object{
        private const val TAG = "PetOpenHelperClass"
    }
}