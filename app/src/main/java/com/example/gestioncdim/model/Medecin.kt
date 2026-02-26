package com.example.gestioncdim.model

data class Medecin(
    var nommed: String,
    var prenmed: Int,
    var emailmed: Int,
    var ids: Int,
) {
    var idmed: Int = -1
    constructor (idmed: Int, nommed: String, prenmed: Int, emailmed: Int, ids: Int): this(nommed, prenmed, emailmed, ids){
        this.idmed = idmed
    }
}
