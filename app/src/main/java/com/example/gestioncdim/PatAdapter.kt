package com.example.gestioncdim

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.example.gestioncdim.db.CidmDataBase
import com.example.gestioncdim.model.Patient

class PatAdapter(
    var conte: Context,
    var resource: Int,
    var valu: ArrayList<Patient>
): ArrayAdapter<Patient> (conte,resource,valu){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val patient = valu[position]
        val convertView = LayoutInflater.from(conte).inflate(resource, parent, false)
        val idtext = convertView.findViewById<TextView>(R.id.idp)
        val nomtext = convertView.findViewById<TextView>(R.id.nomp)
        val prentext = convertView.findViewById<TextView>(R.id.prenp)
        val agetext = convertView.findViewById<TextView>(R.id.agep)
        val photop = convertView.findViewById<ImageView>(R.id.imp)

        val imageShowPopup = convertView.findViewById<ImageView>(R.id.imgPopup)

        val bitmap = getBitmap(patient.imgpat)

        idtext.text = patient.idpat.toString()
        nomtext.text = patient.nompat
        prentext.text = patient.prenpat
        agetext.text = patient.agepat.toString()
        photop.setImageBitmap(bitmap)

        imageShowPopup.setOnClickListener{
            val popupMenu = PopupMenu(conte, imageShowPopup)
            popupMenu.menuInflater.inflate(R.menu.list_popuppat_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.itemShow -> {

                    }

                    R.id.itemDelete -> {
                        val db = CidmDataBase(conte)
                        val resultDelete = db.deletePatient(patient.idpat)
                        if(resultDelete) {
                            valu.removeAt(position)
                            notifyDataSetChanged()
                        }else{
                            Toast.makeText(conte, "Erreur de suppression", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                true
            }
            popupMenu.show()
        }

        return convertView
    }

    fun getBitmap(byteArray: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }
}