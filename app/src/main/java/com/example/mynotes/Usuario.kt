package com.example.mynotes

data class Usuario(
    var uid: String,
    var email: String?,
    var notas: ArrayList<Nota>
)