package com.example.gestioncdim.model

data class User(
    var nom: String,
    var mdp:String
) {
    var id: Int = -1
    constructor (id: Int, nom: String, mdp: String): this(nom, mdp){
        this.id = id
    }

}
