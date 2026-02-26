package com.example.gestioncdim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import com.example.gestioncdim.db.CidmDataBase
import com.example.gestioncdim.model.Patient

class PatientActivity : AppCompatActivity() {

    lateinit var db: CidmDataBase
    var patArray = ArrayList<Patient>()
    lateinit var adapt: PatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        db = CidmDataBase(this)

        /* val backp = findViewById<ImageView>(R.id.backpat)
        backp.setOnClickListener{
           finish()
        } */

        val pati = findViewById<ImageView>(R.id.ajoutpat)
        pati.setOnClickListener {
            val intentToEnrePatActivity = Intent(this, EnrePatActivity::class.java)
            startActivity(intentToEnrePatActivity)
        }

        val listPat = findViewById<ListView>(R.id.lst)
        patArray = db.findPatient()
        adapt = PatAdapter(this, R.layout.model_patient, patArray)
        listPat.adapter = adapt

        listPat.setOnItemClickListener { AdapterView, view, position, id ->
            Intent(this, PlusInfPatActivity::class.java).also {
                startActivity(it)
            }
        }

        registerForContextMenu(listPat)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.patient_menu, menu)
        return super.onCreateOptionsMenu(menu)
    } */

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.list_pat_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info: AdapterView.AdapterContextMenuInfo =item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position: Int = info.position
        when(item.itemId){
            R.id.itemAff ->{
                Intent(this, PlusInfPatActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.itemSup ->{
                adapt.notifyDataSetChanged()
            }
        }
        return super.onContextItemSelected(item)
    }
}