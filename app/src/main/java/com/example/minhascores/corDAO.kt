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

    fun select(): ArrayList<Cor>{
        val listaDeCores = ArrayList<Cor>()
        val colunas = arrayOf("id", "nome", "codigo")

        val cursor = this.banco.readableDatabase.query("cores", colunas, null, null, null, null, "nome")
        cursor.moveToFirst()

        for (i in 1..cursor.count){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nome = cursor.getString(cursor.getColumnIndex("nome"))
            val codigo = cursor.getInt(cursor.getColumnIndex("codigo"))

            listaDeCores.add(Cor(id,nome,codigo))
            cursor.moveToNext()
        }

        return listaDeCores
    }

    fun count(): Int{
        val sql = "select count(*) from CORES"
        val cursor = this.banco.readableDatabase.rawQuery(sql, null)
        cursor.moveToFirst()
        return  cursor.getInt(0)
    }

    fun find(id: Int): Cor?{
        val colunas = arrayOf("id", "nome", "codigo")
        val where = "id = ?"
        val pwhere = arrayOf(id.toString())
        val cursor = this.banco.readableDatabase.query("cores", colunas, null, null, null, null, "nome")
        cursor.moveToFirst()

        if(cursor.count == 1){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nome = cursor.getString(cursor.getColumnIndex("nome"))
            val codigo = cursor.getInt(cursor.getColumnIndex("codigo"))
            return Cor(id, nome, codigo)
        }

        return null
    }

    fun update(cor: Cor){
        val where = "id = ?"
        val pWhere = arrayOf(cor.id.toString())
        val cv = ContentValues()
        cv.put("nome", cor.nome)
        cv.put("codigo", cor.codigo)
        this.banco.writableDatabase.update("cores", cv,where,pWhere)
    }

    fun delete(id: Int){
        val where = "id = ?"
        val pwhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete("cores", where,pwhere)
    }
}