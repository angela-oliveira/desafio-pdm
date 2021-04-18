package com.example.minhascores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.minhascores.Cor

class FormActivity : AppCompatActivity() {

    private var cor: Cor = Cor("", 0)
    private lateinit var btnSalvar: Button
    private lateinit var btnCancelar: Button

    private lateinit var corDAO: corDAO
    private lateinit var nome: EditText
    private lateinit var btnResultado: Button

    private lateinit var verde: SeekBar
    private lateinit var azul: SeekBar
    private lateinit var vermelho: SeekBar

    private var vermelhoCor: Int = 0
    private var verdeCor: Int = 0
    private var azulCor: Int = 0
    private var resultado: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.nome = findViewById(R.id.nome)
        this.btnSalvar = findViewById(R.id.btnSalvar)
        this.btnCancelar = findViewById(R.id.btnCancelar)

        this.verde = findViewById(R.id.barraVerde)
        this.vermelho = findViewById(R.id.barraVermelho)
        this.azul = findViewById(R.id.barraAzul)

        this.corDAO = corDAO(this)
        this.btnResultado = findViewById(R.id.resultCor)


        btnCancelar.setOnClickListener {
            this.finish()
        }
        btnSalvar.setOnClickListener {
            this.addColor()
        }

        this.verde.setOnSeekBarChangeListener(VerdeSeek())
        this.vermelho.setOnSeekBarChangeListener(VermelhoSeek())
        this.azul.setOnSeekBarChangeListener(AzulSeek())

        if(intent.hasExtra("cor")){
            val corNome = intent.getSerializableExtra("cor") as Cor

            this.nome.setText(corNome.nome)
            this.azul.progress = Color.blue(corNome.codigo)
            this.vermelho.progress = Color.red(corNome.codigo)
            this.verde.progress = Color.green(corNome.codigo)
            this.resultado = corNome.codigo

            this.btnResultado.setBackgroundColor(corNome.codigo)
            this.btnResultado.text = Cor.toHex(corNome.codigo)
        }

    }

    private fun addColor() {
        cor.nome = nome.text.toString()
        val intent = Intent()
        intent.putExtra("color", cor)
        setResult(RESULT_OK, intent)
        Toast.makeText(this, "Cor salva com sucesso", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    private fun updateBackgroundColor() {
        val codigo = Color.rgb(vermelhoCor, verdeCor, azulCor)
        cor.codigo = codigo
        this.btnResultado.setBackgroundColor(codigo)
        this.btnResultado.text = cor.toHex()
    }

    inner class VermelhoSeek: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            vermelhoCor = progress
            this@FormActivity.addColor()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    inner class VerdeSeek: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            verdeCor = progress
            this@FormActivity.updateBackgroundColor()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }

    inner class AzulSeek: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            azulCor = progress
            this@FormActivity.updateBackgroundColor()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }
}