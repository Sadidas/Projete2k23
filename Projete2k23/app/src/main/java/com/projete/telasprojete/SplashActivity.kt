package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity

@Suppress("DEPRECATION")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_inicial)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000) // delaying for 4 seconds...
    }
}