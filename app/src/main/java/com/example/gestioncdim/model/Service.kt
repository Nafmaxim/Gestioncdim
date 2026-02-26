package com.example.gestioncdim.model

data class Service(
    var typeser: String,
    var respser: String,
    var imgser: ByteArray
) {
    var idser: Int = -1
    constructor (idser: Int, typeser: String, respser: String, imgser: ByteArray): this(typeser, respser, imgser){
        this.idser = idser
    }
}