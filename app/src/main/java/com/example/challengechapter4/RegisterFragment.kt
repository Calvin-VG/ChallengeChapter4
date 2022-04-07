package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private lateinit var sharedpreferences : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedpreferences = requireContext().getSharedPreferences("Challenge", Context.MODE_PRIVATE)

        btn_daftar.setOnClickListener {
            val username = et_masukkan_username_register.text.toString()
            val email = et_masukkan_email_register.text.toString()
            val password = et_masukkan_password_register.text.toString()
            val konfirmasi_password = et_masukkan_konfirmasi_password_register.text.toString()

            val spedit = sharedpreferences.edit()
            spedit.putString("USERNAME", username)
            spedit.putString("EMAIL", email)
            spedit.putString("PASSWORD", password)
            spedit.putString("KONFIRMASI_PASSWORD", konfirmasi_password)
            spedit.apply()

            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}