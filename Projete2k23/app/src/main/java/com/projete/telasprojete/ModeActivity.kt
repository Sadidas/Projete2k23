package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity

class ModeActivity : ComponentActivity() {

    private var grupo_radio: RadioGroup? = null
    private var radio_mecan: RadioButton? = null
    private var radio_user: RadioButton? = null
    private var btn_confirmar: Button? = null
    private var texto_erro: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mecanic_mode)

        grupo_radio = findViewById(R.id.grupo_radio)
        radio_mecan = findViewById(R.id.radio_mecan)
        radio_user = findViewById(R.id.radio_user)
        btn_confirmar = findViewById(R.id.btnConfirmar)
        texto_erro = findViewById(R.id.tvErro)

        btn_confirmar!!.setOnClickListener {
            if(radio_user!!.isChecked){
                val intent_princ = Intent(this, UserMainActivity::class.java)
                startActivity(intent_princ)
            }
            else if(radio_mecan!!.isChecked){
                val intent_princ = Intent(this, MecanMainActivity::class.java)
                startActivity(intent_princ)
            }
            else{
                texto_erro!!.visibility = TextView.VISIBLE
            }
        }

    }
}