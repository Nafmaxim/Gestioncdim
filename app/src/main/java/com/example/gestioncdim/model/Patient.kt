package com.example.gestioncdim.model

data class Patient(
    var nompat: String,
    var prenpat: String,
    var agepat: Int,
    var imgpat: ByteArray
) {
    var idpat: Int = -1
    constructor (idpat: Int, nompat: String, prenpat: String, agepat: Int, imgpat: ByteArray): this(nompat, prenpat, agepat, imgpat){
        this.idpat = idpat
    }

}
