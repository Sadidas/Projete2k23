package com.projete.telasprojete

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OficinaActivity : ComponentActivity() {

    private val permissao_camera = 1000
    private val imagem_tirou = 1001

    private var imageUri: Uri? = null
    private var imageView: ImageView? = null
    private var botao_foto: ImageButton? = null
    private var botao_mais: Button? = null

    lateinit var tvOficina: TextView

    lateinit var cidade:TextView
    lateinit var estado:TextView
    lateinit var bairro:TextView
    lateinit var endereco:TextView
    lateinit var n:TextView
    lateinit var oficina:ImageView

    val fireStoreDatabase = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_oficina)

        cidade = findViewById(R.id.cidade)
        estado = findViewById(R.id.estado)
        bairro = findViewById(R.id.bairro)
        endereco = findViewById(R.id.endereço)
        n = findViewById(R.id.n)

        imageView = findViewById(R.id.vwQuadrado_preto)
        botao_foto = findViewById(R.id.btnAdicionarFoto)
        botao_mais = findViewById(R.id.btnMais)

        tvOficina = findViewById(R.id.tvNomeOficina)

        findViewById<ImageButton>(R.id.btnvoltar).setOnClickListener {
            val intent_princ = Intent(this, MecanMainActivity::class.java)
            startActivity(intent_princ)
        }

        val usuario_atual = FirebaseAuth.getInstance().currentUser?.uid.toString()

        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Oficina"))
                    tvOficina.setText(resultado.toString())
                }
            }
        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Estado"))
                    estado.setText(resultado.toString())
                }
            }
        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Cidade"))
                    cidade.setText(resultado.toString())
                }
            }
        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("N"))
                    n.setText(resultado.toString())
                }
            }
        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Endereço"))
                    endereco.setText(resultado.toString())
                }
            }
        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("Bairro"))
                    bairro.setText(resultado.toString())
                }
            }

        oficina = findViewById(R.id.btnAdicionarFoto)

        fireStoreDatabase.collection("Usuarios")
            .document(usuario_atual)
            .get()
            .addOnCompleteListener {

                val resultado: StringBuffer = StringBuffer()
                if (it.isSuccessful){
                    resultado.append(it.result.data?.getValue("URL"))
                    Glide.with(this).load(resultado.toString()).into(oficina!!)
                }
            }

        botao_foto!!.setOnClickListener{
            val permissao_dada = pedirPermissaoCamera()
            if (permissao_dada) {
                abrir_interface_camera()
                botao_foto!!.visibility = Button.INVISIBLE
                imageView!!.visibility = ImageView.VISIBLE
                botao_mais!!.visibility = Button.VISIBLE
            }
        }

        botao_mais!!.setOnClickListener {
            val permissao_dada = pedirPermissaoCamera()
            if (permissao_dada){
                abrir_interface_camera()
            }
        }
    }

    private fun pedirPermissaoCamera(): Boolean {
        var permissionGranted = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraPermissionNotGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
            if (cameraPermissionNotGranted) {
                val permission = arrayOf(Manifest.permission.CAMERA)

                requestPermissions(permission, permissao_camera)
            } else {
                permissionGranted = true
            }
        } else {
            permissionGranted = true
        }

        return permissionGranted
    }

    private fun abrir_interface_camera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, R.string.tirar_foto)
        imageUri = contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        startActivityForResult(intent, imagem_tirou)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == imagem_tirou) {
            imageView?.setImageURI(imageUri)
        }
    }
}
