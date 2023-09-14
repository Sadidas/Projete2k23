package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.projete.telasprojete.databinding.TelaConfigBinding


class ConfigActivity : ComponentActivity() {

    private lateinit var binding: TelaConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val click_voltar = findViewById<ImageButton>(R.id.btnvoltar)
        click_voltar.setOnClickListener {
            val intent_voltar = Intent(this, UserMainActivity::class.java)
            startActivity(intent_voltar)
        }

        binding.btnLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltar_telalogin = Intent(this, InicialActivity::class.java)
            startActivity(voltar_telalogin)
            finish()
        }

        binding.btnegg.setOnClickListener {view ->
            navegartelaegg()
        }
    }

    private fun navegartelaegg(){
        val intent = Intent(this,EggActivity::class.java)
        startActivity(intent)
        finish()
    }

}
