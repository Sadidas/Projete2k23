package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class OficinaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_oficina)

        val click_voltar = findViewById<Button>(R.id.btnVoltar)
        click_voltar.setOnClickListener {
            val intent_voltar = Intent(this, MecanMainActivity::class.java)
            startActivity(intent_voltar)
        }
    }
}