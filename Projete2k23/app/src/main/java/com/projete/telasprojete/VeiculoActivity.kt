package com.projete.telasprojete

import android.content.ContentValues
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class VeiculoActivity : ComponentActivity() {

    private val permissao_camera = 1000
    private val imagem_tirou = 1001

    private var imageUri: Uri? = null
    private var imageView: ImageView? = null
    private var botao_foto: ImageButton? = null
    private var botao_mais: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_seuveiculo)


        findViewById<ImageButton>(R.id.btnLista).setOnClickListener {
            val bitmap = pegarImageDeView(imageView!!)
            if(bitmap != null) {
                salvarNaMemoria(bitmap)
            }

            val intent_princ = Intent(this, MainActivity::class.java)
            startActivity(intent_princ)
        }

        imageView = findViewById(R.id.vwQuadrado_preto)
        botao_foto = findViewById(R.id.btnAdicionarFoto)
        botao_mais = findViewById(R.id.btnMais)

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

    private fun pegarImageDeView(imageView: ImageView): Bitmap? {

        var imagem: Bitmap? = null
        try{
            imagem = Bitmap.createBitmap(imageView.measuredWidth, imageView.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(imagem)
            imageView.draw(canvas)
        }
        catch (e: Exception){
            Log.e("Erro", "Falhou em salvar a imagem")
        }
        return imagem
    }

    private fun salvarNaMemoria(bitmap: Bitmap) {

        val nome_imagem = "carro_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            this.contentResolver?.also {resolver ->
                val valor_content = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, nome_imagem)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valor_content)
                fos = imageUri?.let{
                    resolver.openOutputStream(imageUri!!)
                }
            }
        }
        else{
            val diretorio_da_imagem = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imagem = File(diretorio_da_imagem, nome_imagem)
            fos = FileOutputStream(imagem)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Imagem foi salva corretamente", Toast.LENGTH_LONG).show()
            }
        }
    }

