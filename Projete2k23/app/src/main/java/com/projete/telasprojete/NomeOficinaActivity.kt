package com.projete.telasprojete


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaNomeOficinaBinding

class NomeOficinaActivity: ComponentActivity() {

    val Tag: String = "FIRESTORE"
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private lateinit var binding:TelaNomeOficinaBinding

    lateinit var etOficina: EditText
    lateinit var etBairro:EditText
    lateinit var etEndereco:EditText
    lateinit var etNumero:EditText
    lateinit var btnEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = TelaNomeOficinaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etOficina = findViewById(R.id.edit_ofic)
        etBairro = findViewById(R.id.bairro)
        etEndereco = findViewById(R.id.endereco)
        etNumero = findViewById(R.id.numero)

        btnEnviar = findViewById(R.id.btnenviar)

        btnEnviar.setOnClickListener {

            val nome = etOficina.text.toString()
            val bairro = etBairro.text.toString()
            val endereco = etEndereco.text.toString()
            val numero = etNumero.text.toString()

            if(nome.isEmpty()||bairro.isEmpty()||endereco.isEmpty()||numero.isEmpty()){
                val snackbar = com.google.android.material.snackbar.Snackbar.make(it,"Campo Vazio",
                    com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                postar_firestore(nome,bairro,endereco,numero)
                val intent = Intent(this, EstadoMecanicoActivity::class.java)
                startActivity(intent)
            }
        }

    }
    private fun postar_firestore(nome_da_oficina: String, bairro: String, endereco: String, numero: String) {
        val usuario: MutableMap<String, Any> = HashMap()
        val custom_id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        usuario["Oficina"] = nome_da_oficina
        usuario["Bairro"] = bairro
        usuario["Endereço"] = endereco
        usuario["N"] = numero

        fireStoreDatabase.collection("Usuarios")
            .document(custom_id)
            .update(usuario)
            .addOnSuccessListener {
                Log.d(Tag, "Documento autualizado com o id ${custom_id}")
            }
            .addOnFailureListener {
                Log.d(Tag, "Erro autualizando o documento ${it}")
            }
    }
}