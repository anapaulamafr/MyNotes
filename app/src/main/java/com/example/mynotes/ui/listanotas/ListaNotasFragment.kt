package com.example.mynotes.ui.listanotas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.Nota
import com.example.mynotes.databinding.FragmentListaNotasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ListaNotasFragment : Fragment() {

    private var _binding: FragmentListaNotasBinding? = null
    private val binding get() = _binding!!
    private var servicoItemAdapter: RecyclerView.Adapter<NotaItemAdapter.ViewHolder>? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaNotasBinding.inflate(inflater, container, false)

        val listaNotas = ArrayList<Nota>()
        val recyclerView = binding.recyclerViewListaNotas

        recyclerView.adapter = servicoItemAdapter
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = NotaItemAdapter(listaNotas, this.context)
        }

        binding.btnNovaNota.setOnClickListener {
            findNavController().navigate(ListaNotasFragmentDirections.actionListaNotasFragmentToNotaFragment())
        }
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null) {
            firebaseFirestore = FirebaseFirestore.getInstance()
            val dbUser =  firebaseFirestore.collection("users").document(currentUser!!.uid)
            val arrayList = ArrayList<Nota>()

            dbUser.collection("notas")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        arrayList.add(document.toObject<Nota>())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("ERROR", "Error getting documents: ", exception)
                }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}