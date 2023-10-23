package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.projete.telasprojete.databinding.MecanicModeBinding

class ModeActivity : ComponentActivity() {

    private var grupo_radio: RadioGroup? = null
    private var radio_mecan: RadioButton? = null
    private var radio_user: RadioButton? = null
    private var btn_confirmar: Button? = null
    private var texto_erro: TextView? = null
    private lateinit var binding: MecanicModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MecanicModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        grupo_radio = findViewById(R.id.grupo_radio)
        radio_mecan = findViewById(R.id.radio_mecan)
        radio_user = findViewById(R.id.radio_user)
        btn_confirmar = findViewById(R.id.btnenviar)
        texto_erro = findViewById(R.id.tvErro)

        btn_confirmar!!.setOnClickListener {
            if(radio_user!!.isChecked){
                val intent = Intent(this, NomeUserActivity::class.java)
                startActivity(intent)
            }
            else if(radio_mecan!!.isChecked){
                val intent_princ = Intent(this, NomeMecanicoActivity::class.java)
                startActivity(intent_princ)
            }
            else{
                texto_erro!!.visibility = TextView.VISIBLE
            }
        }
    }
}