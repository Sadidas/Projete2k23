package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.projete.telasprojete.databinding.TelaInicial1Binding


class InicialActivity : ComponentActivity() {

    private lateinit var binding: TelaInicial1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaInicial1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSign.setOnClickListener {view ->
            navegarSign()
        }


        val click_Login = findViewById<ImageButton>(R.id.btnEntrar)
        click_Login.setOnClickListener {
            val intent_login = Intent(this, LoginActivity::class.java)
            startActivity(intent_login)
        }
    }
    private fun navegarSign() {
        val intent = Intent(this, SignActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this,UserMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val usuario_atual = FirebaseAuth.getInstance().currentUser

        if (usuario_atual != null) {
            navegarTelaPrincipal()
        }
    }
}
