package com.example.challengechapter4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.SharedPreferences as SharedPreferences

class LoginFragment : Fragment() {
    private lateinit var sharepref : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

        sharepref = requireContext().getSharedPreferences("Challenge", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
//            SHARED PREFERENCES
//            val email = sharepref.getString("EMAIL", "")
//            val password = sharepref.getString("PASSWORD", "")

//            INTENT FRAGMENT
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_home1Fragment)
        }

        tv_buat_akun.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}