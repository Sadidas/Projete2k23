package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MecanMainActivity : ComponentActivity() {

    val fireStoreDatabase = FirebaseFirestore.getInstance()
    lateinit var letrona: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_principal_mecan)
        
        letrona = findViewById(R.id.tvLetrona)
        val usuario_atual = FirebaseAuth.getInstance().currentUser?.uid.toString()

        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Nome"))
                    letrona.setText(resultado.toString().first().toString())
                }
            }

        val click_lista = findViewById<ImageButton>(R.id.btnvoltar)
        click_lista.setOnClickListener {
            val intent_lista = Intent(this, ConfigActivity::class.java)
            startActivity(intent_lista)
        }
        val click_oficina = findViewById<ImageButton>(R.id.btnOficina)
        click_oficina.setOnClickListener {
            val intent_oficina = Intent(this, OficinaActivity::class.java)
            startActivity(intent_oficina)
        }
        val click_cliente = findViewById<ImageButton>(R.id.btnClientes)
        click_cliente.setOnClickListener {
            val intent_cliente = Intent(this, ClientesActivity::class.java)
            startActivity(intent_cliente)
        }
        val click_inutil = findViewById<Button>(R.id.btnInutil)
        click_inutil.setOnClickListener {
            val intent_inutil = Intent(this, EggActivity::class.java)
            startActivity(intent_inutil)
        }
        val click_historico = findViewById<ImageButton>(R.id.perfil)
        click_historico.setOnClickListener {
            val intent_historico_mecan = Intent(this, Perfil1Activity::class.java)
            startActivity(intent_historico_mecan)
        }

    }
}