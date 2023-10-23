package com.projete.telasprojete

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaMecanicoBinding


class MecanicoActivity : ComponentActivity() {

    private lateinit var binding: TelaMecanicoBinding
    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    val Tag: String = "FIRESTORE"
    lateinit var foto1: ImageButton

    lateinit var cidade: TextView
    lateinit var estado: TextView
    lateinit var bairro: TextView
    lateinit var endereco: TextView
    lateinit var n: TextView
    lateinit var oficina: TextView
    lateinit var numero:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaMecanicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foto1 = findViewById(R.id.mecanico3)
        cidade = findViewById(R.id.cidade)
        estado = findViewById(R.id.estado)
        bairro = findViewById(R.id.bairro)
        endereco = findViewById(R.id.endereço)
        n = findViewById(R.id.n)
        oficina = findViewById(R.id.tvNomeOficina3)
        numero = findViewById(R.id.numero)

        binding.btnvoltar.setOnClickListener {
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
        }
        val uidDoUsuario = FirebaseAuth.getInstance().currentUser?.uid

        if (uidDoUsuario != null) {
            fireStoreDatabase.collection("Usuarios")
                .document(uidDoUsuario)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val uid2Valor = documentSnapshot.getString("UID2")

                        if (uid2Valor != null) {
                            Log.d("Firebase", "Valor de UID2: $uid2Valor")
                            // Agora que você tem o UID2, pode chamar o método getBairro
                            getBairro(uid2Valor, bairro)
                            getCidade(uid2Valor, cidade)
                            getEndereco(uid2Valor,endereco)
                            getN(uid2Valor,n)
                            getEstado(uid2Valor,estado)
                            getOficina(uid2Valor,oficina)
                            getURLForUID(uid2Valor,foto1)
                            getNumero(uid2Valor,numero)

                        } else {
                        }
                    } else {
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("Firebase", "Erro ao recuperar dados: $exception")
                }
        } else {
            // Não há usuário autenticado no momento, você pode lidar com isso conforme a lógica do seu aplicativo
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
    private fun getNumero(uidAleatorio: String, textView: TextView) {
        fireStoreDatabase.collection("Usuarios")
            .document(uidAleatorio)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val numero = documentSnapshot.getString("Numero")
                    if (numero != null) {
                        Log.d(Tag, "Numero recuperado do Firestore para UID $uidAleatorio: $numero")
                        textView.text = "$numero" // Define o texto no TextView
                    }
                } else {
                    Log.d(Tag, "Documento com o UID $uidAleatorio não encontrado no Firestore")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Tag, "Erro ao recuperar o bairro do Firestore: $exception")
            }
    }


}
