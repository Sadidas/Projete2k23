package com.projete.telasprojete

import android.content.ContentValues
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat


class VeiculoActivity : ComponentActivity() {

    private val permissao_camera = 1000
    private val permissao_leitura = 1001

    private val imagem_escolha = 1000
    private val imagem_tirou = 1001

    private var imageUri: Uri? = null
    private var imageView: ImageView? = null
    private var botao_foto: ImageButton? = null
    private var botao_mais: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_seuveiculo)

        findViewById<ImageButton>(R.id.btnLista).setOnClickListener {
            val intent_princ = Intent(this, MainActivity::class.java)
            startActivity(intent_princ)
        }

        imageView = findViewById(R.id.vwQuadrado_preto)
        botao_foto = findViewById(R.id.btnAdicionarFoto)
        botao_mais = findViewById(R.id.btnMais)

        botao_foto!!.setOnClickListener{
            val intent_lista = Intent(this, ListafotoActivity::class.java)
            startActivity(intent_lista)
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
            if (permissao_dada) {
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

        } else if (resultCode == Activity.RESULT_OK && requestCode == imagem_escolha) {
            imageView?.setImageURI(data?.data)

        }
    }

}

