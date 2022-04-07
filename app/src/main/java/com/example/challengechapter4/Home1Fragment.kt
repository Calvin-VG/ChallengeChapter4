package com.example.challengechapter4

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterViewAnimator
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home1.*
import kotlinx.android.synthetic.main.fragment_home3.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Home1Fragment : Fragment() {
//    VARIABEL ROOM DATABASE
    private var notedb : NoteDatabase? = null

//    VARIABEL SHARED PREFERENCE
    private lateinit var sharedpref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notedb = NoteDatabase.getInstance(requireContext())
        sharedpref = requireContext().getSharedPreferences("Challenge", Context.MODE_PRIVATE)

        val username = sharedpref.getString("USERNAME", "")
        tv_welcome_username.text = "Welcome, $username!"

        tv_logout.setOnClickListener {
            LogOut()
            Navigation.findNavController(view).navigate(R.id.action_home1Fragment_to_loginFragment)
        }

        fab_add_note.setOnClickListener {
            val interfaceadd = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_home3, null, false)
            val dialogadd = AlertDialog.Builder(requireContext())
                .setView(interfaceadd)
                .create()

            dialogadd.btn_input_data.setOnClickListener {
                val judul = et_input_judul_data.text.toString()
                var catatan = et_input_catatan_data.text.toString()

                var inputdata = notedb?.NoteDao()?.insertNote(Note(null, judul, catatan))
                activity?.runOnUiThread {
                    if (inputdata != 0.toLong()) {
                        Toast.makeText(requireContext(), "Data $judul Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Data $judul Gagal Ditambahkan", Toast.LENGTH_LONG).show()
                    }
                }
            }
            dialogadd.show()
        }

        GetNote()
    }

    fun LogOut(){
        val logout = sharedpref.edit()
        logout.clear()
        logout.apply()
    }

    fun GetNote(){
        rv_list_note.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        GlobalScope.async {
            val datanote = notedb?.NoteDao()?.getallNote()
            activity?.runOnUiThread {
                datanote.let {
                    val adapter = AdapterNote(it!!)
                    rv_list_note.adapter = adapter
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        GetNote()
    }

    override fun onDestroy() {
        super.onDestroy()
        NoteDatabase.destroyInstance()
    }
}