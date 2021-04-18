package com.example.minhascores
import java.io.Serializable

class Cor : Serializable{
    var id: Int
    var nome: String
    var codigo: Int
    constructor(nome: String, codigo: Int){
        this.id = -1
        this.nome = nome
        this.codigo = codigo
    }
    constructor(id:Int, nome: String, codigo: Int){
        this.id = id
        this.nome = nome
        this.codigo = codigo
    }
    companion object {
        fun toHex(color: Int): String {
            return String.format("#%06X", (0xFFFFFF and color))
        }
    }
    fun toHex() : String{
        return String.format("#%06X", (0xFFFFFF and codigo))
    }
    override fun toString(): String {
        return "${id} - ${nome} - ${codigo} - ${toHex()}"
    }
}