package com.example.mynotes.ui.cadastro

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynotes.CEP
import com.example.mynotes.FirebaseFirestoreHelper
import com.example.mynotes.R
import com.example.mynotes.Retrofit
import com.example.mynotes.databinding.FragmentCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CadastroFragment : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val firestore = FirebaseFirestoreHelper()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        with(binding) {
            buttonSignUp.setOnClickListener {
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextSenha.text.toString()
                createAccount(email, password)
            }
        }

        binding.editTextCep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 8) {
                    val call =
                        Retrofit().apiRetrofitService().getEnderecoByCEP(binding.editTextCep.text.toString())

                    call.enqueue(object : Callback<CEP> {

                        override fun onResponse(call: Call<CEP>, response: Response<CEP>) {

                            response?.let {

                                val cep: CEP? = it.body()
                                val endereco: String = "${cep!!.logradouro}, ${cep!!.bairro}, ${cep!!.localidade} - ${cep!!.uf}"
                                binding.editTextEndereco.setText(endereco)
                            }
                        }

                        override fun onFailure(call: Call<CEP>, t: Throwable) {
                        }
                    })
                }
            }
        })
    }

    private fun createAccount(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                val isNewUser: Boolean = task.result.additionalUserInfo!!.isNewUser
                if (task.isSuccessful && isNewUser) {
                    firestore.criarUsuario(email)
                    findNavController().navigate(R.id.listaNotasFragment)
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.editTextEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.error = "Required."
            valid = false
        } else {
            binding.editTextEmail.error = null
        }

        val password = binding.editTextSenha.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editTextSenha.error = "Required."
            valid = false
        } else {
            binding.editTextSenha.error = null
        }

        return valid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}