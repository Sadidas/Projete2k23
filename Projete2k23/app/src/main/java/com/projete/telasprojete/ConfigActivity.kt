package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaConfigBinding


class ConfigActivity : ComponentActivity() {

    private lateinit var binding: TelaConfigBinding
    val fireStoreDatabase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val click_voltar = findViewById<ImageButton>(R.id.btnvoltar)
        click_voltar.setOnClickListener {
            val usuario_atual = FirebaseAuth.getInstance().currentUser
            val uid = usuario_atual?.uid.toString()
            fireStoreDatabase.collection("Usuarios")
                .document(uid)
                .get()
                .addOnCompleteListener {

                    val resultado: StringBuffer = StringBuffer()
                    if (it.isSuccessful) {
                        resultado.append(it.result.data?.getValue("Mecanico"))
                        val isMecanico = resultado.toString().toBoolean()
                        if (isMecanico) {
                            val intent = Intent(this,MecanMainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val intent = Intent(this,UserMainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
        }

        binding.btnLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltar_telalogin = Intent(this, InicialActivity::class.java)
            startActivity(voltar_telalogin)
            finish()
        }

        binding.btnegg.setOnClickListener {view ->
            navegartelaegg()
        }
    }

    private fun navegartelaegg(){
        val intent = Intent(this,EggActivity::class.java)
        startActivity(intent)
        finish()
    }

}
