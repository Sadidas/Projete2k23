package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class ListafotoActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_de_opcoes_foto)

        findViewById<ImageButton>(R.id.btnLista).setOnClickListener {
            val intent_princ = Intent(this, MainActivity::class.java)
            startActivity(intent_princ)
        }
    }
}