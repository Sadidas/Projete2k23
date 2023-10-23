package com.projete.telasprojete

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.projete.telasprojete.databinding.TelaFotoBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FotoActivity : ComponentActivity() {


    val Tag: String = "FIRESTORE"
    val fireStoreDatabase = FirebaseFirestore.getInstance()

    private lateinit var binding: TelaFotoBinding
    private var imageReference = Firebase.storage.reference
    private var currentFile: Uri? = null
    private var url: String = ""
    var ja_upload: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storageRef = FirebaseStorage.getInstance().reference

        Glide.with(this).load(url).into(binding.image2)

        binding.imageView.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                imageLauncher.launch(it)
            }
        }
        binding.btnvoltar.setOnClickListener {
            val usuario_atual = FirebaseAuth.getInstance().currentUser?.uid.toString()
            fireStoreDatabase.collection("Usuarios")
                .document(usuario_atual)
                .get()
                .addOnCompleteListener {

                    val resultado: StringBuffer = StringBuffer()
                    if (it.isSuccessful){
                        resultado.append(it.result.data?.getValue("Tem Foto"))
                        val tem = resultado.toString().toBoolean()
                        if(tem){
                            val intent = Intent(this, VeiculoActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, NomeUserActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
        }

        binding.btnUpload.setOnClickListener {

            currentFile?.let { file ->
                val storageRef = FirebaseStorage.getInstance().reference
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                val imageRef = storageRef.child("veiculo/$uid")
                val uploadTask = imageRef.putFile(file)

                uploadTask.addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val url = uri.toString()
                        postar_firestore(url)

                        Glide.with(this)
                            .load(url)
                            .into(binding.image2)

                        Toast.makeText(this, "Upload success. Image URL: $url", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, FotoPerfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Error getting download URL: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Error on upload: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result?.data?.data?.let {
                currentFile = it
                binding.imageView.setImageURI(it)
                Log.d("FotoActivity", "Image URI set successfully")
            }
        } else {
            Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show()
            Log.d("FotoActivity", "Image selection canceled")
        }
    }

    private fun postar_firestore(url_do_storage: String) {
        val usuario: MutableMap<String, Any> = HashMap()
        val custom_id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        usuario["URL"] = url_do_storage
        usuario["Tem Foto"] = true

        fireStoreDatabase.collection("Usuarios")
            .document(custom_id)
            .update(usuario)
            .addOnSuccessListener {
                Log.d(Tag, "Documento autualizado com o id ${custom_id}")
            }
            .addOnFailureListener {
                Log.d(Tag, "Erro autualizando o documento ${it}")
            }
    }
}



