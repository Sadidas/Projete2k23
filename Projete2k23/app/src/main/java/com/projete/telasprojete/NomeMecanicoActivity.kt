package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.projete.telasprojete.databinding.TelaNomeMecBinding

class NomeMecanicoActivity : ComponentActivity() {

    private lateinit var binding: TelaNomeMecBinding
    private var nome_do_user: String? = null
    private var edit_text: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaNomeMecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edit_text!!.findViewById<EditText>(R.id.edit_nome)

        val click_continuar = findViewById<Button>(R.id.btnenviar)
        click_continuar.setOnClickListener {
            nome_do_user = edit_text!!.text.toString()
            if (nome_do_user != null) {
                val intent_main = Intent(this, MecanMainActivity::class.java)
                intent_main.putExtra("Nome_User", nome_do_user)
                startActivity(intent_main)
            }
        }

        binding.btnvoltar.setOnClickListener {
            val intent = Intent(this,ModeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
