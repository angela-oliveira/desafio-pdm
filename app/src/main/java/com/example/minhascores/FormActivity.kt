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
import com.example.minhascores.Cor
import com.example.minhascores.corDAO

class FormActivity : AppCompatActivity() {
    private lateinit var corVerde: SeekBar
    private lateinit var corAzul: SeekBar
    private lateinit var corVermelho: SeekBar
    private lateinit var corDAO: corDAO
    private lateinit var nome: EditText
    private lateinit var botaoSalvar: Button
    private lateinit var botaoCancelar: Button
    private var corResultado: Int = 0
    private lateinit var botaoResultado: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.corVerde = findViewById(R.id.barraVerde)
        this.corVermelho = findViewById(R.id.barraVermelho)
        this.corAzul = findViewById(R.id.barraAzul)
        this.botaoSalvar = findViewById(R.id.botaoSalvar)
        this.botaoCancelar = findViewById(R.id.botaoCancelar)
        this.nome = findViewById(R.id.nome)
        this.corDAO = corDAO(this)
        this.botaoResultado = findViewById(R.id.resultCor)

        if(intent.hasExtra("cor")){
            val cor1 = intent.getSerializableExtra("cor") as Cor

            this.nome.setText(cor1.nome)
            this.corAzul.progress = Color.blue(cor1.codigo)
            this.corVermelho.progress = Color.red(cor1.codigo)
            this.corVerde.progress = Color.green(cor1.codigo)
            this.corResultado = cor1.codigo
            transformaHexadecimal(cor1.codigo)
        }

        this.corVerde.setOnSeekBarChangeListener(corConfig())
        this.corVermelho.setOnSeekBarChangeListener(corConfig())
        this.corAzul.setOnSeekBarChangeListener(corConfig())

        this.botaoSalvar.setOnClickListener(clickSalvar())
        this.botaoCancelar.setOnClickListener(clickCancelar())
        this.botaoResultado.setOnClickListener(clickResultado())

    }

    fun transformaHexadecimal(corNumero: Int){
        this.botaoResultado.setBackgroundColor(corNumero)
        this.botaoResultado.text = Cor.toHex(corNumero)
    }

    inner class clickCancelar(): View.OnClickListener{
        override fun onClick(v: View?) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    inner class clickResultado(): View.OnClickListener{
        override fun onClick(v: View?) {
            var corHexa = (v as Button).text
            var barra = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var result = ClipData.newPlainText("resultado", corHexa)
            barra.setPrimaryClip(result)
        }
    }

    inner class clickSalvar(): View.OnClickListener{
        override fun onClick(v: View?) {
            var nomeCor = this@FormActivity.nome.text.toString()
            var cor = Cor(nomeCor, corResultado)

            var intent = Intent()
            if(intent.hasExtra("cor") && intent.hasExtra("Lista_Cores")){
                var novaIntent = (intent.getSerializableExtra("cor") as Cor).id
                intent.putExtra("Lista_Cores", intent.getIntExtra("Lista_Cores", corResultado))
                cor.id = novaIntent
                this@FormActivity.corDAO.update(cor)
            }
            else{
                var idCor = this@FormActivity.corDAO.insert(cor)
                cor.id = idCor.toString().toInt()
            }

            intent.putExtra("cor", cor)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    inner class corConfig(): SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            var azul = this@FormActivity.corAzul.progress
            var verde = this@FormActivity.corVerde.progress
            var vermelho = this@FormActivity.corVermelho.progress

            var corFormada: Int = Color.rgb(vermelho, verde, azul)
            this@FormActivity.corResultado = corFormada
            this@FormActivity.transformaHexadecimal(corFormada)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            TODO("Not yet implemented")
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            TODO("Not yet implemented")
        }
    }
}