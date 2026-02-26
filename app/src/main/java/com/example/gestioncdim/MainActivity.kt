package com.example.gestioncdim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.gestioncdim.db.CidmDataBase

class MainActivity : AppCompatActivity() {

    lateinit var db: CidmDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = CidmDataBase(this)

        val connect = findViewById<Button>(R.id.login)
        val name = findViewById<EditText>(R.id.nom)
        val passe = findViewById<EditText>(R.id.mdp)
        val texerr = findViewById<TextView>(R.id.error)

        /*val bout = findViewById<ImageView>(R.id.ajoutautre)
        bout.setOnClickListener {
            val intentToEnreUserActivity = Intent(this, EnreUserActivity::class.java)
            startActivity(intentToEnreUserActivity)
        } */

        connect.setOnClickListener{
            val recupnom = name.text.toString()
            val recupmdp = passe.text.toString()

            if(recupnom.trim().isEmpty() || recupmdp.trim().isEmpty()){
                Toast.makeText(this, "Vous devez remplir tous les champs!", Toast.LENGTH_LONG)
                    .show()
            }else{
                val user = db.findUser(recupnom, recupmdp)
                if(user != null){
                    val recupids=user.id
                    name.setText("")
                    passe.setText("")

                    val intentToHomeActivity = Intent(this,HomeActivity::class.java)
                    startActivity(intentToHomeActivity)

                }else{
                    texerr.text = "* Nom d'utilisateur ou mot de passe incorrect!"
                    texerr.visibility = View.VISIBLE
                }
            }
        }
    }
}