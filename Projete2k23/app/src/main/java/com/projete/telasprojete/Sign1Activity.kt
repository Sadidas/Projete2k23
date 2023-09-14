package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.projete.telasprojete.databinding.TelaCadastro1Binding



class Sign1Activity : ComponentActivity() {

    private lateinit var binding: TelaCadastro1Binding
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaCadastro1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnenviar.setOnClickListener { view ->

            val senha = binding.editSenhaCadastro.text.toString()
            var email = intent.getStringExtra("email")
            email = email.toString()

            if (senha.isEmpty()) {
                val snackbar = com.google.android.material.snackbar.Snackbar.make(
                    view, "Campo Vazio",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
                        if (cadastro.isSuccessful) {
                            val snackbar = com.google.android.material.snackbar.Snackbar.make(
                                view, "Sucesso ao cadastrar usuario", Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                            if (cadastro.isSuccessful){
                                navegarTelaMode()
                            }
                        }
                }.addOnFailureListener{exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Senha deve conter menos 6 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Email Ivalido"
                        is FirebaseAuthUserCollisionException -> "Email já cadastrado"
                        is FirebaseNetworkException -> "Sem Conexão"
                        else -> "Erro ao Cadastrar Usuario!"
                    }
                    val snackbar = com.google.android.material.snackbar.Snackbar.make(
                        view, mensagemErro,
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                }
            }
        }

        val click_voltar = findViewById<ImageButton>(R.id.btnvoltar)
        click_voltar.setOnClickListener {
            val intent_voltar = Intent(this, SignActivity::class.java)
            startActivity(intent_voltar)
        }


    }

    private fun navegarTelaMode(){
        val intent = Intent(this,ModeActivity::class.java)
        startActivity(intent)
        finish()
    }

}



