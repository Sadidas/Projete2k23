package com.projete.telasprojete

import android.content.Intent
import android.widget.Button
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity

class ListafotoActivity: ComponentActivity() {

    private var fase: Int = 1
    lateinit var modelo: String
    lateinit var marca: String

    private var pergunta_modelo: TextView? = null
    private var edittext_modelo: EditText? = null
    private var voltar: Button? = null
    private var confirmar: Button? = null
    private var lista: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_de_opcoes_foto)

        pergunta_modelo = findViewById(R.id.tvModelo)
        edittext_modelo = findViewById(R.id.etModelo)
        voltar = findViewById(R.id.btnVoltar)
        confirmar = findViewById(R.id.btnConfirmar)
        lista = findViewById(R.id.btnLista)

        fun modelo_aceito(): String {
            val texto = edittext_modelo as EditText
            val modelo = texto.text.toString()
            if (modelo != "") {
                fase += 1
                pergunta_modelo!!.setText(getString(R.string.pergunta_marca))
                edittext_modelo!!.text.clear()
            }
            return modelo
        }

        fun marca_aceita(): String {
            val texto = edittext_modelo as EditText
            val marca = texto.text.toString()
            if (marca != "") {
                fase += 1
                pergunta_modelo!!.visibility = TextView.INVISIBLE
                edittext_modelo!!.visibility = EditText.INVISIBLE
            }
            return marca
        }

        lista!!.setOnClickListener {
            val intent_princ = Intent(this, MainActivity::class.java)
            startActivity(intent_princ)
        }
        confirmar!!.setOnClickListener {
            when(fase){
                1 -> modelo = modelo_aceito()
                2 -> marca = marca_aceita()
                3 -> print("danone")
            }
        }
        voltar!!.setOnClickListener {
            when(fase){
                1 -> print("Monkey")
                2 -> print("Frog")
                3 -> print("danone")
            }
        }
    }
}