package com.example.mynotes

data class Usuario(
    var uid: String,
    var nome: String?,
    var notas: ArrayList<Nota>
)