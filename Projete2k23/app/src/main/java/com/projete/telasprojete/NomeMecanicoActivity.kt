package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaNomeMecBinding

class NomeMecanicoActivity : ComponentActivity() {

    val Tag: String = "FIRESTORE"
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private lateinit var binding: TelaNomeMecBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = TelaNomeMecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnenviar.setOnClickListener {view ->

            val nome = binding.editnome.text.toString()

            if(nome.isEmpty()){
                val snackbar = com.google.android.material.snackbar.Snackbar.make(view,"Campo Vazio",
                    com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                postar_firestore(nome)
                val intent = Intent(this, NomeOficinaActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnvoltar.setOnClickListener {
            val intent = Intent(this,ModeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun postar_firestore(nome_user: String) {
        val usuario: MutableMap<String, Any> = HashMap()
        val custom_id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        usuario["Nome"] = nome_user
        usuario["Mecanico"] = true

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