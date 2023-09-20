package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity

class MecanMainActivity : ComponentActivity() {

    private var letrona: TextView? = null
    private var nome_do_user: String = intent.getStringExtra("Nome_user").toString()
    private var primeira_letra: Char = nome_do_user.first()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_principal_mecan)

        letrona = findViewById(R.id.tvLetrona)
        letrona!!.text = primeira_letra.toString()

        val click_lista = findViewById<ImageButton>(R.id.btnLista)
        click_lista.setOnClickListener {
            val intent_lista = Intent(this, ConfigActivity::class.java)
            startActivity(intent_lista)
        }
        val click_oficina = findViewById<ImageButton>(R.id.btnOficina)
        click_oficina.setOnClickListener {
            val intent_oficina = Intent(this, OficinaActivity::class.java)
            startActivity(intent_oficina)
        }
        val click_cliente = findViewById<ImageButton>(R.id.btnClientes)
        click_cliente.setOnClickListener {
            val intent_cliente = Intent(this, ClientesActivity::class.java)
            startActivity(intent_cliente)
        }
        val click_inutil = findViewById<Button>(R.id.btnInutil)
        click_inutil.setOnClickListener {
            val intent_inutil = Intent(this, EggActivity::class.java)
            startActivity(intent_inutil)
        }
        val click_historico = findViewById<ImageButton>(R.id.btnHistorico)
        click_historico.setOnClickListener {
            val intent_historico_mecan = Intent(this, HistoricoMecanActivity::class.java)
            startActivity(intent_historico_mecan)
        }

    }
}