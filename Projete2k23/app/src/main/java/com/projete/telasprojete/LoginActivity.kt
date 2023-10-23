package com.projete.telasprojete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.projete.telasprojete.databinding.TelaLogin0Binding


class LoginActivity : ComponentActivity() {

    private lateinit var binding:TelaLogin0Binding
    private val auth = FirebaseAuth.getInstance()
    private lateinit var googleSignClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaLogin0Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("439199896758-go5at1sedid55g1b5pmtt57tv1senm8f.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogle.setOnClickListener {
            sigIn()
        }

        binding.btnLogin.setOnClickListener {view ->
            val email = binding.editEmailLogin.text.toString()
            val senha = binding.editSenhaLogin.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {autenticacao ->
                    if (autenticacao.isSuccessful){
                        navegarTelaPrincipal()
                    }
                }.addOnCompleteListener {
                    val snackbar = Snackbar.make(view, "Erro ao fazer login", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }
        }

        val click_sign = findViewById<ImageButton>(R.id.btnSign)
        click_sign.setOnClickListener {
            val intent_sign = Intent(this, SignActivity::class.java)
            startActivity(intent_sign)
        }
        val click_voltar = findViewById<ImageButton>(R.id.btnvoltar)
        click_voltar.setOnClickListener {
            val intent_voltar = Intent(this, InicialActivity::class.java)
            startActivity(intent_voltar)
        }

    }
    private fun navegarTelaPrincipal(){
        val intent = Intent(this,UserMainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun sigIn(){
        val intent = googleSignClient.signInIntent
        abreActivity.launch(intent)
    }
    var abreActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result: ActivityResult ->

        if(result.resultCode == RESULT_OK){
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            try{
                val conta = task.getResult(ApiException::class.java)
                loginComGoogle(conta.idToken!!)
            }catch (exception: ApiException){

            }
        }
    }
    private fun loginComGoogle(token: String){
        val credencial = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credencial).addOnCompleteListener(this)
        {
            task: Task<AuthResult> ->
            if(task.isSuccessful){
                Toast.makeText(
                    baseContext, "Sucesso ao fazer login",
                    Toast.LENGTH_SHORT
                ).show()
                navegarTelaPrincipal()
            }else{
                Toast.makeText(
                    baseContext, "Erro ao fazer login",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}
