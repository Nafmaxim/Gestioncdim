package com.example.gestioncdim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gestioncdim.db.CidmDataBase
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var basedon: CidmDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homefrag = HomeFragment()
        val patfrag = PatientFragment()

        setCurrentFragment(homefrag)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.accueil1 -> setCurrentFragment(homefrag)
                R.id.patient1 -> setCurrentFragment(patfrag)
            }
            true
        }


        basedon = CidmDataBase(this)



       /* val maison = findViewById<ImageView>(R.id.accueil1)
        val patient = findViewById<ImageView>(R.id.patient1)

       maison.setOnClickListener{

           /* maison.setImageResource(R.drawable.home_bl)
            val intentToHomeActivity = Intent(this,HomeActivity::class.java)
            startActivity(intentToHomeActivity) */

            supportFragmentManager.beginTransaction().apply{
                replace(R.id.granfrag, homefrag)
                commit()
            }

        }

        patient.setOnClickListener{

            supportFragmentManager.beginTransaction().apply{
                replace(R.id.granfrag, patfrag)
                commit()
            }

        } */

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.granfrag, fragment)
            commit()
        }
}