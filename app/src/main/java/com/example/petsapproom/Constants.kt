package com.example.petsapproom

import android.provider.BaseColumns

class Constants {
    companion object {
        const val DATABASE_NAME = "petsDatabase"
        const val TABLE_PETS_NAME = "pets"
        const val DATABASE_VERSION = 1
        const val COLUMN_NAME = "name"
        const val COLUMN_BREED = "breed"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_WEIGHT = "weight"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXIST $TABLE_PETS_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                    "$COLUMN_NAME TEXT NOT NULL, " +
                    "$COLUMN_BREED TEXT, " +
                    "$COLUMN_GENDER INTEGER NOT NULL, " +
                    "$COLUMN_WEIGHT INTEGER DEFAULT 0);"

        const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS $TABLE_PETS_NAME"
    }

}