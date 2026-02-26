package com.example.gestioncdim.db;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gestioncdim.model.Consultation
import com.example.gestioncdim.model.Medecin
import com.example.gestioncdim.model.Patient
import com.example.gestioncdim.model.Service
import com.example.gestioncdim.model.User

class CidmDataBase (contu : Context) : SQLiteOpenHelper(
        contu,
        DB_NAME,
        null,
        DB_VERSION

) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Creation des tables
        val createTableUser = """
            CREATE TABLE $USERS_TABLE_NAME(
                $USER_ID integer PRIMARY KEY,
                $NOMUTI varchar(50),
                $MOTDEPASSE varchar(20)
            )
        """.trimIndent()
        db?.execSQL(createTableUser)

        val createTablePatient = """
            CREATE TABLE $PATIENT_TABLE_NAME(
                $PAT_ID integer PRIMARY KEY,
                $NOMPA varchar(50),
                $PRENPA varchar(50),
                $AGE int(3),
                $IMAGE blob
            )
        """.trimIndent()
        db?.execSQL(createTablePatient)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //La suppression des anciennnes tables
        //et la recreation des nouvelles tables
        db?.execSQL("DROP TABLE IF EXISTS $USERS_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $PATIENT_TABLE_NAME")
        onCreate(db)
    }

    fun addUser(user: User): Boolean{
        //Inserer un nouvel utilisateur dans la base de donnee

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_ID, user.id)
        values.put(NOMUTI, user.nom)
        values.put(MOTDEPASSE, user.mdp)

        //insert into user(nom,mdp) values(user.nom, user.mdp)
        val result = db.insert(USERS_TABLE_NAME, null, values).toInt()

        db.close()

        return result != -1
    }

    fun findUser(nom: String, mdp: String): User? {

        //Authentification
        val user: User? = null
        val db = this.readableDatabase
        val selectArgs = arrayOf(nom, mdp)

        val cursor = db.query(USERS_TABLE_NAME, null, "$NOMUTI=? AND $MOTDEPASSE=?", selectArgs,
            null, null, null)
        if(cursor != null) {
            if(cursor.moveToFirst()){
                val idu = cursor.getInt(0)
                val nomu = cursor.getString(1)
                val mdpu = cursor.getString(2)
                val user = User(idu, nomu, mdpu)
                return user
            }
        }

        db.close()
        return user
    }


    fun addPat(patient: Patient): Boolean{
        //Inserer un nouveau patient

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PAT_ID, patient.idpat)
        values.put(NOMPA, patient.nompat)
        values.put(PRENPA, patient.prenpat)
        values.put(AGE, patient.agepat)
        values.put(IMAGE, patient.imgpat)

        //insert into user(nom,mdp) values(user.nom, user.mdp)
        val result = db.insert(PATIENT_TABLE_NAME, null, values).toInt()

        db.close()

        return result != -1
    }

    fun findPatient(): ArrayList<Patient> {

        val patients = ArrayList<Patient>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $PATIENT_TABLE_NAME"

        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val id = cursor.getInt(0)
                    val nom = cursor.getString(1)
                    val pren = cursor.getString(2)
                    val age = cursor.getInt(3)
                    val photo = cursor.getBlob(4)
                    val patient = Patient(id, nom, pren, age, photo)
                    patients.add(patient)
                }while(cursor.moveToNext())
            }
        }

        db.close()
        return patients
    }

    fun deletePatient(id: Int): Boolean{
        val db = writableDatabase

        val rowDeleted = db.delete(PATIENT_TABLE_NAME, "idp=?", arrayOf(id.toString()))
        return rowDeleted>0
    }


    fun addConsult(consultation: Consultation): Boolean{
        //Inserer un nouveau patient

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DATCON, consultation.datcon)
        values.put(IDM, consultation.idm)
        values.put(IDP, consultation.idp)

        //insert into user(nom,mdp) values(user.nom, user.mdp)
        val result = db.insert(CONSULTATION_TABLE_NAME, null, values).toInt()

        db.close()

        return result != -1
    }


    fun addMedecin(medecin: Medecin): Boolean{
        //Inserer un nouveau patient

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NOMME, medecin.nommed)
        values.put(PRENME, medecin.prenmed)
        values.put(EMAILME, medecin.emailmed)
        values.put(IDS, medecin.ids)

        //insert into user(nom,mdp) values(user.nom, user.mdp)
        val result = db.insert(MEDECIN_TABLE_NAME, null, values).toInt()

        db.close()
        return result != -1
    }


    fun addService(service: Service): Boolean{
        //Inserer un nouveau patient

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TYPES, service.typeser)
        values.put(RESPS, service.respser)
        values.put(IMAGES, service.imgser)

        //insert into user(nom,mdp) values(user.nom, user.mdp)
        val result = db.insert(SERVICE_TABLE_NAME, null, values).toInt()

        db.close()
        return result != -1
    }

    companion object{
        private val DB_NAME = "cidm_db"
        private val DB_VERSION = 2

        //UTILISATEURS
        private val USERS_TABLE_NAME = "users"
        private val USER_ID = "id"
        private val NOMUTI = "name"
        private val MOTDEPASSE = "mdp"

        //PATIENTS
        private val PATIENT_TABLE_NAME = "patients"
        private val PAT_ID = "idp"
        private val NOMPA = "nomp"
        private val PRENPA = "prenp"
        private val AGE = "agep"
        private val IMAGE = "imap"

        //CONSULTATIONS
        private val CONSULTATION_TABLE_NAME = "consultation"
        private val CON_ID = "idc"
        private val DATCON = "datecons"
        private val IDM = "idm"
        private val IDP = "idp"

        //MEDECIN
        private val MEDECIN_TABLE_NAME = "Medecins"
        private val MED_ID = "idm"
        private val NOMME = "nomm"
        private val PRENME = "prenm"
        private val EMAILME = "emailm"
        private val IDS = "IdS"

        //SERVICE
        private val SERVICE_TABLE_NAME = "Services"
        private val SER_ID = "IdS"
        private val TYPES = "TypeS"
        private val RESPS = "ResponsableS"
        private val IMAGES = "ImageS"


    }
}
