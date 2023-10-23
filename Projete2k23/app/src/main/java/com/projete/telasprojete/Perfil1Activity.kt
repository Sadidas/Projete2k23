package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaPerfil1Binding

class Perfil1Activity : ComponentActivity() {

    private lateinit var binding: TelaPerfil1Binding

    lateinit var perfil: ImageView
    lateinit var nome: EditText
    lateinit var email:EditText
    lateinit var telefone:EditText

    val fireStoreDatabase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = TelaPerfil1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        perfil = findViewById(R.id.perfil)
        val roundDrawable = ContextCompat.getDrawable(this, R.drawable.round_shape)
        perfil.background = roundDrawable
        perfil.clipToOutline = true

        nome = findViewById(R.id.nome)
        email = findViewById(R.id.email)
        telefone = findViewById(R.id.telefone)

        val usuario_atual = FirebaseAuth.getInstance().currentUser?.uid.toString()

        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("URL2"))
                    Glide.with(this).load(resultado.toString()).into(perfil!!)
                }
            }

        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Nome"))
                    nome.setText(resultado.toString())
                }
            }
        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Email"))
                    email.setText(resultado.toString())
                }
            }
        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Numero"))
                    telefone.setText(resultado.toString())
                }
            }

        binding.btnvoltar.setOnClickListener {
            val intent = Intent(this, MecanMainActivity::class.java)
            startActivity(intent)
        }
    }
}
