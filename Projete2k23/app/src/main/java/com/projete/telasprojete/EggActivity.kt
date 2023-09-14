package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.projete.telasprojete.databinding.LagostaModeBinding

class EggActivity : ComponentActivity() {

    private lateinit var binding: LagostaModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LagostaModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnvoltar.setOnClickListener {view ->

            navegartelaconfig()
        }

    }
    private fun navegartelaconfig(){
        val intent = Intent(this,ConfigActivity::class.java)
        startActivity(intent)
        finish()
    }
}