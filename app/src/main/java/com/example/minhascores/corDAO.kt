package com.example.minhascores

import android.content.ContentValues
import android.content.Context
import android.util.Log

class corDAO {
    private var banco: bancoHelper;
    constructor(context: Context){
        this.banco = bancoHelper(context)
    }

    fun insert(cor: Cor){
        val cv = ContentValues()
        cv.put("nome", cor.nome)
        cv.put("codigo", cor.codigo)
        this.banco.writableDatabase.insert("cores",null,cv)

    }


    fun delete(id: Int){
        val where = "id = ?"
        val pwhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete("cores", where,pwhere)
    }
}