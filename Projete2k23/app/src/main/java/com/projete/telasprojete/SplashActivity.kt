package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_inicial)

        Handler().postDelayed({
            val intent = Intent(this, InicialActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000) // delaying for 4 seconds...
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this,UserMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val usuario_atual = FirebaseAuth.getInstance().currentUser
        if(usuario_atual!=null){
            navegarTelaPrincipal()
        }
    }
}