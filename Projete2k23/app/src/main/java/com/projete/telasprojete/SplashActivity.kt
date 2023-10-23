package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class SplashActivity : ComponentActivity() {

    val fireStoreDatabase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_inicial)

        Handler().postDelayed({
        }, 7000)
        val intent = Intent(this, InicialActivity::class.java)
        startActivity(intent)
    }

    private fun navegarTelaPrincipalUser(){
        val intent = Intent(this,UserMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navegarTelaPrincipalMecan(){
        val intent = Intent(this,MecanMainActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val usuario_atual = FirebaseAuth.getInstance().currentUser
        if (usuario_atual != null) {
            val uid = usuario_atual.uid.toString()
            fireStoreDatabase.collection("Usuarios")
                .document(uid)
                .get()
                .addOnCompleteListener {

                    val resultado: StringBuffer = StringBuffer()
                    if (it.isSuccessful) {
                        resultado.append(it.result.data?.getValue("Mecanico"))
                        val isMecanico = resultado.toString().toBoolean()
                        if (isMecanico) {
                            navegarTelaPrincipalMecan()
                        } else {
                            navegarTelaPrincipalUser()
                        }
                    }
                }
        } else {
            val intent = Intent(this, InicialActivity::class.java)
            startActivity(intent)
        }
    }
}