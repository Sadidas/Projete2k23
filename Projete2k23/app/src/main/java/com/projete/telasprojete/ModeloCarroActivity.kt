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
import com.projete.telasprojete.databinding.TelaModeloBinding

class ModeloCarroActivity : AppCompatActivity() {

    val Tag: String = "FIRESTORE"
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    lateinit var spinner: Spinner
    lateinit var adapter:ArrayAdapter<String>
    private lateinit var binding: TelaModeloBinding
    private var opcaoEscolhida: String = ""
    private var opcaoEscolhida1: String = ""
    private var opcaoEscolhida2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaModeloBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = arrayListOf<String>(
            "Toyota",
            "Honda",
            "Ford",
            "Volkswagen",
            "Chevrolet",
            "BMW",
            "Mercedes-Benz",
            "Audi",
            "Tesla",
            "Jeep",)
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
                Toast.makeText(applicationContext,"Escolha"+adapter.getItem(position),Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        var data1 = arrayListOf<String>("Corolla", "Camry", "RAV4", "Hilux",
                                        "Prius", "Civic", "Accord", "CR-V", "Fit",
                                        "HR-V", "F-150", "Mustang", "Explorer", "Escape",
                                        "Focus", "Golf", "Jetta", "Passat",
                                        "Tiguan", "Beetle", "Silverado",
                                        "Equinox", "Malibu", "Tahoe",
                                        "Suburban")
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
                Toast.makeText(
                    applicationContext,
                    "Escolha" + adapter.getItem(position),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        var data2 = arrayListOf<String>(
            "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990",
            "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001",
            "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012",
            "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023")
        adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,data2)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner =findViewById(R.id.spinner2) as Spinner
        spinner.adapter=adapter
        spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                opcaoEscolhida2 = data2[position]
                Toast.makeText(applicationContext,"Escolha"+adapter.getItem(position),Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnenviar.setOnClickListener(){
            postar_firestore(opcaoEscolhida, opcaoEscolhida1,opcaoEscolhida2)
            val intent = Intent(this, FotoActivity::class.java)
            startActivity(intent)
        }
    }
    private fun postar_firestore(marca: String, modelo: String, ano: String) {
        val modeloData: MutableMap<String, Any> = HashMap()
        val custom_id1 = FirebaseAuth.getInstance().currentUser?.uid.toString()
        modeloData["Marca"] = marca
        modeloData["Modelo"] = modelo
        modeloData["Ano"] = ano

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
