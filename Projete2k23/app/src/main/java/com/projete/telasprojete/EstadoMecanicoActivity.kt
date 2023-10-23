package com.projete.telasprojete

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaEstado1Binding

class EstadoMecanicoActivity : AppCompatActivity() {
    val Tag: String = "FIRESTORE"
    lateinit var spinner: Spinner
    lateinit var adapter:ArrayAdapter<String>
    private lateinit var binding: TelaEstado1Binding
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var opcaoEscolhida: String = ""
    private var opcaoEscolhida1: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaEstado1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = arrayListOf<String>(
            "Conceição dos Ouros",
            "Itajubá",
            "Natércia",
            "Pedralva",
            "Pouso Alegre",
            "Santa Rita do Sapucaí",
            "Varginha")
        adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner =findViewById(R.id.spinner) as Spinner
        spinner.adapter=adapter
        spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                opcaoEscolhida = data[position]
                Toast.makeText(applicationContext,"Escolha sua cidade"+adapter.getItem(position),Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        var data1 = arrayListOf<String>("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO")
        adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,data1)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner =findViewById(R.id.spinner1) as Spinner
        spinner.adapter=adapter
        spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                opcaoEscolhida1 = data1[position]
                Toast.makeText(applicationContext, "Escolha seu Estado" + adapter.getItem(position),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.btnenviar.setOnClickListener(){
            postar_firestore(opcaoEscolhida, opcaoEscolhida1)
            val intent = Intent(this, FotoMecActivity::class.java)
            startActivity(intent)
        }
        binding.btnvoltar.setOnClickListener {
            val intent = Intent(this, NomeOficinaActivity::class.java)
            startActivity(intent)
        }
    }
    private fun postar_firestore(cidade: String, estado: String) {
        val modeloData: MutableMap<String, Any> = HashMap()
        val custom_id1 = FirebaseAuth.getInstance().currentUser?.uid.toString()
        modeloData["Estado"] = estado
        modeloData["Cidade"] = cidade
        modeloData["Mecanico"] = true

        fireStoreDatabase.collection("Usuarios")
            .document(custom_id1)
            .update(modeloData)
            .addOnSuccessListener {
                Log.d(Tag, "Documento adicionado com o id ${custom_id1}")
            }
            .addOnFailureListener {
                Log.d(Tag, "Erro adicionando o documento ${it}")
            }
    }
}
