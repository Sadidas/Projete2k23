package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import com.projete.telasprojete.databinding.TelaCadastroBinding

class SignActivity : ComponentActivity() {

    private lateinit var binding: TelaCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = TelaCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnenviar.setOnClickListener {view ->

            val email = binding.editEmailCadastro.text.toString()

            if(email.isEmpty()){
                val snackbar = com.google.android.material.snackbar.Snackbar.make(view,"Campo Vazio",Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                val intent_sign1 = Intent(this, Sign1Activity::class.java)
                intent_sign1.putExtra("email",email)
                startActivity(intent_sign1)

            }
        }
        val click_voltar = findViewById<ImageButton>(R.id.btnvoltar)
        click_voltar.setOnClickListener {
            val intent_voltar = Intent(this, InicialActivity::class.java)
            startActivity(intent_voltar)
        }
    }
}
