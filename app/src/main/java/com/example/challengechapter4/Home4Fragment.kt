package com.example.challengechapter4

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home4.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class Home4Fragment : Fragment() {

    private var noteupdate : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteupdate = NoteDatabase.getInstance(requireContext())

        val getnotedata = arguments?.getParcelable<Note>("DATA_NOTE") as Note

        et_edit_judul_data.setText(getnotedata.judul)
        et_edit_catatan_data.setText(getnotedata.catatan)

        btn_edit_data.setOnClickListener {
            getnotedata.judul = et_edit_judul_data.text.toString()
            getnotedata.catatan = et_edit_catatan_data.text.toString()

            GlobalScope.async {
                val update = noteupdate?.NoteDao()?.updateNote(getnotedata)
                activity?.runOnUiThread {
                    if (update != 0){
                        Toast.makeText(requireContext(), "Data ${getnotedata.judul} Berhasil Dihapus", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(requireContext(), "Data ${getnotedata.judul} Gagal Dihapus", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}