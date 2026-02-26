package com.example.gestioncdim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.gestioncdim.db.CidmDataBase

class Home2Activity : AppCompatActivity() {

    lateinit var basedon: CidmDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        basedon = CidmDataBase(this)

        //val maison = findViewById<ImageView>(R.id.accueil)
        val patient = findViewById<ImageView>(R.id.patient)
        val scan = findViewById<ImageView>(R.id.scan)

        val idmedecin = intent.getStringExtra("idmedecin")

        /*maison.setOnClickListener{

            /* maison.setImageResource(R.drawable.home_bl)
             val intentToHomeActivity = Intent(this,HomeActivity::class.java)
             startActivity(intentToHomeActivity) */

            supportFragmentManager.beginTransaction().apply{
                replace(R.id.granfrag, homefrag)
                commit()
            }

        } */

        patient.setOnClickListener{

           /* supportFragmentManager.beginTransaction().apply{
                replace(R.id.granfrag, patfrag)
                commit()
            } */


            /* val intentToConsultActivity = Intent(this,EnreConsultActivity::class.java)
             intentToConsultActivity.putExtra("idmedecin","$idmedecin")
             startActivity(intentToConsultActivity) */

             val intentToPatientActivity = Intent(this,PatientActivity::class.java)
             startActivity(intentToPatientActivity)

        }

        scan.setOnClickListener{

            val intentToServActivity = Intent(this,EnreServActivity::class.java)
            intentToServActivity.putExtra("idmedecin","$idmedecin")
            startActivity(intentToServActivity)

        }

    }
}