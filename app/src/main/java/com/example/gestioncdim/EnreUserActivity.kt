package com.example.gestioncdim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gestioncdim.db.CidmDataBase
import com.example.gestioncdim.model.User

class EnreUserActivity : AppCompatActivity() {

    lateinit var db: CidmDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enre_user)

        db = CidmDataBase(this)

        val recupId = findViewById<EditText>(R.id.idu)
        val recupNom = findViewById<EditText>(R.id.nompa)
        val recupMdp = findViewById<EditText>(R.id.prenpa)
        val enre = findViewById<Button>(R.id.enregistrer)
        val derr = findViewById<TextView>(R.id.erroru)

        enre.setOnClickListener{
            derr.visibility = View.INVISIBLE
            derr.text = ""

            val idstr = recupId.text.toString()
            val nom = recupNom.text.toString()
            val pwd = recupMdp.text.toString()

            //check if null

            if(idstr.isEmpty() || nom.isEmpty() || pwd.isEmpty()){
                derr.text = "Vous devez renseigner toutes les informations"
                derr.visibility = View.VISIBLE
            }
            else{
                val id = idstr.toInt()
                val utilisateur = User(id, nom, pwd)
                val isInserted = db.addUser(utilisateur)
                if(isInserted){
                    Toast.makeText(this, "Compte créer avec succès",
                        Toast.LENGTH_SHORT).show()
                    /*Intent(this, HomeActivity::class.java).also{
                        startActivity(it)
                    } */
                    finish()
                }
            }
        }

    }
}