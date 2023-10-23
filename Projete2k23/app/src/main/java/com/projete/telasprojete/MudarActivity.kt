package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaTrocamecanicoBinding

class MudarActivity : ComponentActivity() {

    private lateinit var binding: TelaTrocamecanicoBinding
    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    val Tag: String = "FIRESTORE"
    lateinit var foto1: ImageButton
    lateinit var foto2: ImageButton

    lateinit var cidade: TextView
    lateinit var estado: TextView
    lateinit var bairro: TextView
    lateinit var endereco: TextView
    lateinit var n: TextView
    lateinit var oficina:TextView

    lateinit var cidade2:TextView
    lateinit var estado2:TextView
    lateinit var bairro2:TextView
    lateinit var endereco2:TextView
    lateinit var n2:TextView
    lateinit var oficina2:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TelaTrocamecanicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foto1 = findViewById(R.id.mecanico)
        foto2 = findViewById(R.id.mecanico2)

        cidade = findViewById(R.id.cidade)
        estado = findViewById(R.id.estado)
        bairro = findViewById(R.id.bairro)
        endereco = findViewById(R.id.endereço)
        n = findViewById(R.id.n)
        oficina = findViewById(R.id.tvNomeOficina)

        cidade2 = findViewById(R.id.cidade2)
        estado2 = findViewById(R.id.estado2)
        bairro2 = findViewById(R.id.bairro2)
        endereco2 = findViewById(R.id.endereço2)
        n2 = findViewById(R.id.n2)
        oficina2 = findViewById(R.id.tvNomeOficina2)

        binding.btnvoltar.setOnClickListener {
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
        }
        fireStoreDatabase.collection("Usuarios")
            .whereEqualTo("Mecanico", true)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() >= 2) {
                    val documents = querySnapshot.documents
                    val randomUid1 = documents[0].id
                    val random = randomUid1.toString()
                    val randomUid2 = documents[1].id
                    val random2 = randomUid2.toString()
                    getURLForUID(random, foto1)
                    getURLForUID(random2, foto2)
                    getBairro(random, bairro)
                    getBairro(random2, bairro2)
                    getEndereco(random, endereco)
                    getEndereco(random2, endereco2)
                    getN(random, n)
                    getN(random2, n2)
                    getCidade(random, cidade)
                    getCidade(random2, cidade2)
                    getEstado(random, estado)
                    getEstado(random2, estado2)
                    getOficina(random, oficina)
                    getOficina(random2,oficina2)

                    binding.uidTextView1.text = "$randomUid1"
                    binding.uidTextView2.text = randomUid2

                } else {
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Erro", "Erro ao buscar mecânicos: $exception")
            }





        binding.mecanico.setOnClickListener {
            val uidAleatorio = binding.uidTextView1.text.toString()
            postar_firestore(uidAleatorio)
        }

        binding.mecanico2.setOnClickListener {
            val uidAleatorio = binding.uidTextView2.text.toString()
             postar_firestore(uidAleatorio)
        }

    }
    private fun getURLForUID(uidAleatorio: String, imageView: ImageView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio) // Use o UID aleatório como identificador do documento
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val url = documentSnapshot.getString("URL")
                    if (url != null) {
                        Log.d(Tag, "URL recuperada do Firestore para UID $uidAleatorio: $url")
                        Glide.with(this).load(url).into(imageView)
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar a URL do Firestore: $exception")
            }
    }
    private fun getBairro(uidAleatorio: String, textView: TextView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val bairro = documentSnapshot.getString("Bairro")
                    if (bairro != null) {
                        Log.d(Tag, "Bairro recuperado do Firestore para UID $uidAleatorio: $bairro")
                        textView.text = "$bairro" // Define o texto no TextView
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar o bairro do Firestore: $exception")
            }
    }
    private fun getEndereco(uidAleatorio: String, textView: TextView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val endereco = documentSnapshot.getString("Endereço")
                    if (endereco != null) {
                        Log.d(Tag, "Endereço recuperado do Firestore para UID $uidAleatorio: $endereco")
                        textView.text = "$endereco" // Define o texto no TextView
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar o bairro do Firestore: $exception")
            }
    }

    private fun getN(uidAleatorio: String, textView: TextView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val n = documentSnapshot.getString("N")
                    if (n != null) {
                        Log.d(Tag, "numero recuperado do Firestore para UID $uidAleatorio: $n")
                        textView.text = "$n" // Define o texto no TextView
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar o bairro do Firestore: $exception")
            }
    }

    private fun getCidade(uidAleatorio: String, textView: TextView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val cidade = documentSnapshot.getString("Cidade")
                    if (cidade != null) {
                        Log.d(Tag, "Cidade recuperado do Firestore para UID $uidAleatorio: $cidade")
                        textView.text = "$cidade" // Define o texto no TextView
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar o bairro do Firestore: $exception")
            }
    }

    private fun getOficina(uidAleatorio: String, textView: TextView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val oficina = documentSnapshot.getString("Oficina")
                    if (oficina != null) {
                        Log.d(Tag, "Oficina recuperado do Firestore para UID $uidAleatorio: $oficina")
                        textView.text = "$oficina" // Define o texto no TextView
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar o bairro do Firestore: $exception")
            }
    }

    private fun getEstado(uidAleatorio: String, textView: TextView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val estado = documentSnapshot.getString("Estado")
                    if (estado != null) {
                        Log.d(Tag, "Estado recuperado do Firestore para UID $uidAleatorio: $estado")
                        textView.text = "$estado" // Define o texto no TextView
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar o bairro do Firestore: $exception")
            }
    }

    private fun postar_firestore(UID2: String) {
        val usuario: MutableMap<String, Any> = HashMap()
        val custom_id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        usuario["UID2"] = UID2

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
