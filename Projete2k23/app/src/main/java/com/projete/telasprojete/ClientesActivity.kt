package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class ClientesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_clientes)

        val click_voltar = findViewById<ImageButton>(R.id.btnvoltar)
        click_voltar.setOnClickListener {
            val intent_voltar = Intent(this, MecanMainActivity::class.java)
            startActivity(intent_voltar)
        }
    }
}