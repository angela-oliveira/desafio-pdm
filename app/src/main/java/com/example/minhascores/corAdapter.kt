package com.example.minhascores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class corAdapter  (private val context: Context, private val dataSource: ArrayList<Cor>) : BaseAdapter() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(pos: Int): Cor {
        return dataSource[pos]
    }

    override fun getItemId(pos : Int): Long {
        return pos.toLong()
    }

    override fun getView(position: Int, converView: View?, parent: ViewGroup) : View
    {
        val rowView = inflater.inflate(R.layout.activity_lista, parent, false);
        val currCor = getItem(position)

        val name = rowView.findViewById<TextView>(R.id.imagemCor)
        val code = rowView.findViewById<TextView>(R.id.nome)
        val icon = rowView.findViewById<ImageView>(R.id.codigoCor)

        name.text = currCor.nome
        code.text = currCor.toHex()
        icon.setColorFilter(currCor.codigo)

        return rowView
    }

    fun add(cor: Cor)
    {
        dataSource.add(cor)
        this.notifyDataSetChanged()
    }

    fun update(cor: Cor, position: Int)
    {
        dataSource[position] = cor
        this.notifyDataSetChanged()
    }

    fun delete(pos: Int)
    {
        dataSource.removeAt(pos)
        this.notifyDataSetChanged()
    }

    fun setArray(arr : ArrayList<Cor>)
    {
        this.dataSource.clear()
        this.dataSource.addAll(arr)
        this.notifyDataSetChanged()
    }
}