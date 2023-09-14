package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_principal)

        val click_veiculo = findViewById<ImageButton>(R.id.btnVeiculos)
        click_veiculo.setOnClickListener {
            val intent_veicu = Intent(this, VeiculoActivity::class.java)
            startActivity(intent_veicu)
        }
        val click_mecanico = findViewById<ImageButton>(R.id.btnMecanico)
        click_mecanico.setOnClickListener {
            val intent_mecan = Intent(this, MecanicoActivity::class.java)
            startActivity(intent_mecan)
        }
        val click_mudar = findViewById<ImageButton>(R.id.btnMudarMecanico)
        click_mudar.setOnClickListener {
            val intent_mudar = Intent(this, MudarActivity::class.java)
            startActivity(intent_mudar)
        }
    }
}
