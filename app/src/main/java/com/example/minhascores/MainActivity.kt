  package com.example.minhascores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var corDAO: corDAO
    private lateinit var cores : ArrayList<Cor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.corDAO = corDAO(this)
        this.cores = ArrayList<Cor>()
        val lvCores = findViewById<ListView>(R.id.listaCores)
        val adapter = corAdapter(this, this.cores)
        lvCores.adapter = adapter

        lvCores.setOnItemClickListener { _, _, i, _ ->
            run {
                val itemclicked = adapter.getItem(i)
                val intent = Intent(this, FormActivity::class.java)
                intent.putExtra("COR", itemclicked)
                intent.putExtra("POS", i)
                startActivityForResult(intent, EDIT)
            }
        }

        lvCores.setOnItemLongClickListener { _, _, i, _ ->
            run {
                val cor = adapter.getItem(i)
                this.corDAO.delete(cor.id)
                adapter.delete(i)
                Toast.makeText(this, "${cor.nome} removido!", Toast.LENGTH_SHORT).show()
                return@setOnItemLongClickListener true
            }
        }

        val fabAdd = findViewById<FloatingActionButton>(R.id.addButton)

        fabAdd.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivityForResult(intent, ADD)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.i("MAIN", "resumed!")
        this.cores = this.corDAO.select()

        (findViewById<ListView>(R.id.listaCores).adapter as corAdapter).setArray(this.cores)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val cor = data?.getSerializableExtra("COR") as Cor
            val adapter = findViewById<ListView>(R.id.listaCores).adapter as corAdapter
            when (requestCode) {
                ADD -> {
                    this.corDAO.insert(cor)
                    Toast.makeText(this, "Cor adicionada!", Toast.LENGTH_SHORT).show()
                }
                EDIT -> {
                    this.corDAO.update(cor)
                    val pos = data.getIntExtra("POS", 0)
                    adapter.update(cor, pos)
                    Toast.makeText(this, "Cor atualizada!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val ADD = 1
        const val EDIT = 2
    }
}