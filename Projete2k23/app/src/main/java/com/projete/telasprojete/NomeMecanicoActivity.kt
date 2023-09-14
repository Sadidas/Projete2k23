package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import com.projete.telasprojete.databinding.TelaNomeBinding
import com.projete.telasprojete.databinding.TelaNomeMecBinding

class NomeMecanicoActivity : ComponentActivity() {

    private lateinit var binding: TelaNomeMecBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = TelaNomeMecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnvoltar.setOnClickListener {view ->
            navegarTelaMode()
        }

    }
    private fun navegarTelaMode(){
        val intent = Intent(this,ModeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
