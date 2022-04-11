package com.example.mynotes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class FirebaseFirestoreHelper {
    val firebaseFirestore = Firebase.firestore

    fun criarUsuario() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        val novoUsuario = Usuario(
            firebaseUser!!.uid, firebaseUser!!.email, ArrayList()
        )
        firebaseFirestore.collection("usuarios").document(firebaseUser.uid).collection("notas")
    }

    fun criarNota(nota: Nota) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val documento = firebaseFirestore.collection("usuarios").document(firebaseUser!!.uid)
            .collection("notas")

        val mapNota = hashMapOf(
            "conteudo" to nota.conteudo
        )

        documento.add(mapNota)
    }

    fun excluirNota(nota: Nota) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        firebaseFirestore.collection("usuarios").document(firebaseUser!!.uid)
            .collection("notas").document(nota.conteudo).delete()

    }

    suspend fun getNotas(): ArrayList<String> {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val notas = ArrayList<String>()
        val documento = firebaseFirestore.collection("usuarios").document(firebaseUser!!.uid)
            .collection("notas")

        val snapshot = documento.get().await()
        for (doc in snapshot!!.documents) {
            notas.add(doc.toString())
        }

        return notas
    }
}