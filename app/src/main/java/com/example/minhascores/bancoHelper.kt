package com.example.minhascores

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class bancoHelper(context: Context) : SQLiteOpenHelper (context, "cores.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE cor (id integer primary key autoincrement, nome text, codigo integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db?.execSQL("drop table cor")
        this.onCreate(db)
    }
}