package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MecanMainActivity : ComponentActivity() {

    private var btnVoltar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_principal_mecan)

        btnVoltar = findViewById(R.id.btnVoltar)
        btnVoltar!!.setOnClickListener {
            val intent_mode = Intent(this, ModeActivity::class.java)
            startActivity(intent_mode)
        }

    }
}