package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.projete.telasprojete.databinding.TelaPrincipalUserBinding


class UserMainActivity : ComponentActivity() {

    val fireStoreDatabase = FirebaseFirestore.getInstance()
    lateinit var letrona: TextView
    private lateinit var binding: TelaPrincipalUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaPrincipalUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        letrona = findViewById(R.id.tvLetrona)

        val usuario_atual = FirebaseAuth.getInstance().currentUser?.uid.toString()

        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Nome"))
                    letrona.setText(resultado.toString().first().uppercase())
                }
            }

        val click_veiculo = findViewById<ImageButton>(R.id.btnVeiculos)
        click_veiculo.setOnClickListener {
            fireStoreDatabase.collection("Usuarios")
                .document(usuario_atual)
                .get()
                .addOnCompleteListener {

                    val resultado: StringBuffer = StringBuffer()
                    if (it.isSuccessful) {
                        resultado.append(it.result.data?.getValue("Tem Foto"))
                    }
                    val tem = resultado.toString().toBoolean()
                    if (tem) {
                        val intent = Intent(this, VeiculoActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, FotoActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
        }

        val click_mecanico = findViewById<ImageButton>(R.id.btnMecanico)
        click_mecanico.setOnClickListener {
            val intent_mecan = Intent(this, MecanicoActivity::class.java)
            startActivity(intent_mecan)
        }
        val click_mudar = findViewById<ImageButton>(R.id.btnMudarMecanico)
        click_mudar.setOnClickListener {
            val intent_mudar = Intent(this, MudarActivity::class.java)
            startActivity(intent_mudar)
        }
        val click_voltar = findViewById<ImageButton>(R.id.btnvoltar)
        click_voltar.setOnClickListener {
            val intent_modo = Intent(this, ConfigActivity::class.java)
            startActivity(intent_modo)
        }
        val click_historico = findViewById<ImageButton>(R.id.perfil)
        click_historico.setOnClickListener {
            val intent_historico = Intent(this, PerfilActivity::class.java)
            startActivity(intent_historico)
        }
        binding.perfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

    }
}
