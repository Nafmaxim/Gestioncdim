package com.example.gestioncdim

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.gestioncdim.db.CidmDataBase
import com.example.gestioncdim.model.Service
import java.io.ByteArrayOutputStream

class EnreServActivity : AppCompatActivity() {

    lateinit var btnEnre: Button
    lateinit var typeser: EditText
    lateinit var respser: EditText
    lateinit var pren: EditText
    lateinit var age: EditText
    lateinit var imaSer: ImageView

    var bitmap: Bitmap? = null

    lateinit var db: CidmDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enre_serv_activiy)

        db = CidmDataBase(this)

        btnEnre = findViewById(R.id.enregistrers)
        typeser = findViewById(R.id.types)
        respser = findViewById(R.id.resps)
        imaSer = findViewById(R.id.imgserv)

        val backs = findViewById<ImageView>(R.id.backs)
        backs.setOnClickListener{
            finish()
        }

        /*val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { data ->
            val inputStream = contentResolver.openInputStream(data!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            imaPat.setImageBitmap(bitmap)
        } */

        imaSer.setOnClickListener{
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            intentImg.type = "image/*"
            startActivityForResult(intentImg, 100)
        }

        btnEnre.setOnClickListener{

            val recuptyp = typeser.text.toString()
            val recupres = respser.text.toString()

            if(recuptyp.isEmpty() || recupres.isEmpty() || bitmap == null){
                Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show()
            }else {
                //if(bitmap == null){val defaut = }
                val recupImg: ByteArray = getBytes(bitmap!!)
                val service = Service(recuptyp, recupres, recupImg)
                val isInserted = db.addService(service)

                if(isInserted){
                    Toast.makeText(this, "Le service a bien été enregistré",
                        Toast.LENGTH_SHORT).show()
                    //Vider le formulaire
                    typeser.setText("")
                    respser.setText("")
                    imaSer.setImageResource(R.drawable.image)
                }

            }

        }

    }

    fun getBytes(bitmap: Bitmap): ByteArray{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            val uri = data?.data

            val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            imaSer.setImageBitmap(bitmap)
        }
    }

    }
