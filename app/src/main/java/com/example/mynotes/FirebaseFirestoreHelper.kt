package com.example.mynotes

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class FirebaseFirestoreHelper {
    private val firebaseFirestore = Firebase.firestore
    private val firebaseUser = FirebaseAuth.getInstance().currentUser


    fun criarUsuario() {
        firebaseFirestore.collection("usuarios").document(firebaseUser!!.uid)
    }

    fun criarNota(texto: String) {
        val mapNota = hashMapOf(
            "id" to UUID.randomUUID().toString(),
            "conteudo" to texto
        )

        firebaseFirestore.collection("notas").document(mapNota["id"]!!).set(mapNota)
    }

    fun excluirNota(nota: Nota) {
        firebaseFirestore.collection("notas").document(nota.id!!).delete()
    }

    fun editarNota(texto: String, nota: Nota) {
        firebaseFirestore.collection("notas").document(nota.id!!)
            .update("conteudo", texto)
    }
}
