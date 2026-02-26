package com.example.gestioncdim.model

data class Consultation(
    var datcon: String,
    var idp: Int,
    var idm: Int,
) {
    var idcon: Int = -1
    constructor (idcon: Int, datcon: String, idp: Int, idm: Int): this(datcon, idp, idm){
        this.idcon = idcon
    }

}
