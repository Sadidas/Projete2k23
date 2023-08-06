package com.projete.telasprojete

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.ImageButton

class VeiculoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_seuveiculo)

        val click_voltar = findViewById<ImageButton>(R.id.btnLista)
        click_voltar.setOnClickListener {
            val intent_princ = Intent(this, MainActivity::class.java)
            startActivity(intent_princ)
        }
    }
}
