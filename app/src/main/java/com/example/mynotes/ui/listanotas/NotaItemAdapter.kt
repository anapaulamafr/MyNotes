package com.example.mynotes.ui.listanotas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.Nota
import com.example.mynotes.databinding.ItemNotaBinding

class NotaItemAdapter(private val listaNotas: ArrayList<Nota>) :
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
        val bundle = Bundle()

        with(holder.itemView) {
            binding.textViewTextoNota.text = nota.conteudo?.let { retornarFraseEncurtada(it) }
            bundle.putString("ID", nota.id)
            bundle.putString("CONTEUDO", nota.conteudo)
            holder.itemView.setOnClickListener {
                findNavController().navigate(ListaNotasFragmentDirections.actionListaNotasFragmentToNotaFragment(bundle))
            }
        }
    }

    override fun getItemCount(): Int {
        return listaNotas.count()
    }

    fun retornarFraseEncurtada(frase: String): String {
        if (frase.length < 29) {
            return frase
        }
        else {
            return "${frase.substring(0, 30)}..."
        }
    }
}