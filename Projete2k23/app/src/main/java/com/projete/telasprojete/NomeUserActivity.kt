package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import com.projete.telasprojete.databinding.TelaNomeBinding

class NomeUserActivity : ComponentActivity() {

    private lateinit var binding: TelaNomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = TelaNomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnvoltar.setOnClickListener {view ->
            navegarTelaMode()
        }

        binding.btnenviar.setOnClickListener {view ->
            val intent = Intent(this, EstadoUserActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun navegarTelaMode(){
        val intent = Intent(this,ModeActivity::class.java)
        startActivity(intent)
        finish()
    }

}
