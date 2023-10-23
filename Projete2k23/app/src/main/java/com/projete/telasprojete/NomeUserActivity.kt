package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaNomeBinding

class NomeUserActivity : ComponentActivity() {

    val Tag: String = "FIRESTORE"
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private lateinit var binding: TelaNomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaNomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnvoltar.setOnClickListener {
            navegarTelaMode()
        }

        binding.btnenviar.setOnClickListener {

            val nome = binding.editNome.text.toString()

            if(nome.isNotEmpty()) {

                postar_firestore(nome)
                val intent = Intent(this, EstadoUserActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val snackbar = com.google.android.material.snackbar.Snackbar.make(
                    it, "Nome inválido",
                    Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }

    }
    private fun navegarTelaMode(){
        val intent = Intent(this,ModeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun postar_firestore(nome_user: String) {
        val usuario: MutableMap<String, Any> = HashMap()
        val custom_id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        usuario["Nome"] = nome_user
        usuario["Mecanico"] = false

        fireStoreDatabase.collection("Usuarios")
            .document(custom_id)
            .update(usuario)
            .addOnSuccessListener {
                Log.d(Tag, "Documento adicionado com o id ${custom_id}")
            }
            .addOnFailureListener {
                Log.d(Tag, "Erro adicionando o documento ${it}")
            }
    }

}
