package com.example.gestioncdim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.gestioncdim.db.CidmDataBase
import com.example.gestioncdim.model.Consultation
import com.example.gestioncdim.model.Patient

class EnreConsultActivity : AppCompatActivity() {

    lateinit var db: CidmDataBase
    var patArray = ArrayList<Patient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enre_consult)

        db = CidmDataBase(this)
        patArray = db.findPatient()
        var idp = 0

        val enregistrer = findViewById<Button>(R.id.enregistrer)
        val dat = findViewById<TextView>(R.id.datcon)
        val idmedecin= intent.getStringExtra("idmedecin")


        /*val items = listOf("Patient1","Patient2","Patient3","Patient4","Patient5")

        patArray.add("Patient1")
        patArray.add("Patient2")
        patArray.add("Patient3")
        patArray.add("Patient4")
        patArray.add("Patient5") */

        val nomPatArray = ArrayList<String>()

        for(i in 0..1){
            nomPatArray.add(patArray[i].nompat +" "+ patArray[i].prenpat)
        }


        val autoComplete: AutoCompleteTextView = findViewById(R.id.combo_pat)

        val adapter = ArrayAdapter(this, R.layout.list_consult_item, nomPatArray)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
            /*val itemSelected = adapterView.getItemAtPosition(i)
            val nomp = patArray[i].nompat
            Toast.makeText(this, "$itemSelected et $nomp", Toast.LENGTH_SHORT).show() */
            idp = patArray[i].idpat
        }

        enregistrer.setOnClickListener{

            val recupdat = dat.text.toString()

            if(recupdat.isEmpty() || autoComplete == null){
                Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show()
            }else {
                //if(bitmap == null){val defaut = }
                val idm = idmedecin!!.toInt()
                val consultation = Consultation(recupdat, idp, idm)
                val isInserted = db.addConsult(consultation)

                if (isInserted) {
                    Toast.makeText(
                        this, "Consultation enregistrée avec succès",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Vider le formulaire
                    dat.setText("")
                    //autoComplete.setText("")
                }
            }

            }

    }
}