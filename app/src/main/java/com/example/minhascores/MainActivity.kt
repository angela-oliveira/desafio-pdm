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

    private lateinit var lista: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.corDAO = corDAO(this)
        this.cores = ArrayList<Cor>()
        val listaCores = findViewById<ListView>(R.id.listaCores)
        val adapter = corAdapter(this, this.cores)
        listaCores.adapter = adapter

        listaCores.setOnItemClickListener { _, _, i, _ ->
            run {
                val itemclicked = adapter.getItem(i)
                val intent = Intent(this, FormActivity::class.java)
                intent.putExtra("COR", itemclicked)
                intent.putExtra("POS", i)
                startActivityForResult(intent, 2)
            }
        }

        listaCores.setOnItemLongClickListener { _, _, i, _ ->
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
            startActivityForResult(intent, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val cor = data?.getSerializableExtra("COR") as Cor
                if (cor != null) {

                    this.corDAO.insert(cor)
                }
                Toast.makeText(this, "Cor adicionada", Toast.LENGTH_SHORT).show()
            }
        }
    }

}