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
import com.example.gestioncdim.model.Patient
import java.io.ByteArrayOutputStream

class EnrePatActivity : AppCompatActivity() {

    lateinit var btnEnre: Button
    lateinit var iden: EditText
    lateinit var nom: EditText
    lateinit var pren: EditText
    lateinit var age: EditText
    lateinit var imaPat: ImageView

    var bitmap: Bitmap? = null

    lateinit var db: CidmDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enre_pat)

        db = CidmDataBase(this)

        btnEnre = findViewById(R.id.enregistrer)
        nom = findViewById(R.id.nompa)
        pren = findViewById(R.id.prenpa)
        age = findViewById(R.id.agepa)
        imaPat = findViewById(R.id.photopa)

        val backp = findViewById<ImageView>(R.id.backp)
        backp.setOnClickListener{
            finish()
        }

        /*val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { data ->
            val inputStream = contentResolver.openInputStream(data!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            imaPat.setImageBitmap(bitmap)
        } */

        imaPat.setOnClickListener{
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            intentImg.type = "image/*"
            startActivityForResult(intentImg, 100)
        }

        btnEnre.setOnClickListener{

            val recupId = iden.text.toString()
            val recupNom = nom.text.toString()
            val recupPren = pren.text.toString()
            val recupAge = age.text.toString()

            if(recupId.isEmpty() || recupNom.isEmpty() || recupPren.isEmpty() || recupAge.isEmpty() || bitmap == null){
                Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show()
            }else {
                //if(bitmap == null){val defaut = }
                val recupImg: ByteArray = getBytes(bitmap!!)
                val id = recupId.toInt()
                val agep = recupAge.toInt()
                val patient = Patient(id, recupNom, recupPren, agep, recupImg)
                val isInserted = db.addPat(patient)

                if(isInserted){
                    Toast.makeText(this, "Le patient a bien été enregistré",
                        Toast.LENGTH_SHORT).show()
                    //Vider le formulaire
                    iden.setText("")
                    nom.setText("")
                    pren.setText("")
                    age.setText("")
                    imaPat.setImageResource(R.drawable.user)
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
            imaPat.setImageBitmap(bitmap)
        }
    }

}