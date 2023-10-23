package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaCadastroBinding

class SignActivity : ComponentActivity() {

    val Tag: String = "FIRESTORE"
    val fireStoreDatabase = FirebaseFirestore.getInstance()

    private lateinit var binding: TelaCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnenviar.setOnClickListener { view ->

            val senha = binding.editSenhaCadastro.text.toString()
            val email = binding.editEmailCadastro.text.toString()
            val numero = binding.editNumero.text.toString()

            if (senha.isEmpty()|| email.isEmpty()||numero.isEmpty()) {
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
                                postar_firestore(email, senha, numero)
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

    private fun postar_firestore(nome_email: String, nome_senha: String, numero:String) {
        val usuario: MutableMap<String, Any> = HashMap()
        val custom_id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        usuario["Email"] = nome_email
        usuario["Senha"] = nome_senha
        usuario["Numero"] = numero
        usuario["Tem Foto"] = false

        fireStoreDatabase.collection("Usuarios")
            .document(custom_id)
            .set(usuario)
            .addOnSuccessListener {
                Log.d(Tag, "Documento adicionado com o id ${custom_id}")
            }
            .addOnFailureListener {
                Log.d(Tag, "Erro adicionando o documento ${it}")
            }
    }

}



