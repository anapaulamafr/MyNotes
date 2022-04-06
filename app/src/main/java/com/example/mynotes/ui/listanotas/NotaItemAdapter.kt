package com.example.mynotes.ui.listanotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.Nota
import com.example.mynotes.databinding.ItemNotaBinding

class NotaItemAdapter(private val listaNotas: ArrayList<Nota>, val context: Context) :
    RecyclerView.Adapter<NotaItemAdapter.ViewHolder>() {
    private var _binding: ItemNotaBinding? = null
    private val binding get() = _binding!!

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding =
            ItemNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val nota = listaNotas[position]

        with(holder.itemView) {
            binding.textViewTextoNota.text = retornarFraseEncurtada(nota.toString())
            holder.itemView.setOnClickListener {
                findNavController().navigate(ListaNotasFragmentDirections.actionListaNotasFragmentToNotaFragment())
            }
        }

    }

    override fun getItemCount(): Int {
        return listaNotas.count()
    }

    fun retornarFraseEncurtada(frase: String): String {
        val fraseEncurtada: String = "${frase.substring(0, 30)}..."
        return fraseEncurtada
    }
}