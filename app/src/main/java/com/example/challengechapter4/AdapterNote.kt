package com.example.challengechapter4

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home4.*
import kotlinx.android.synthetic.main.item_note.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AdapterNote(val listDataNote : List<Note>) : RecyclerView.Adapter<AdapterNote.ViewHolder>() {

    private var notedatabase : NoteDatabase? = null

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNote.ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: AdapterNote.ViewHolder, position: Int) {
        holder.itemView.tv_judul_note.text = listDataNote[position].judul
        holder.itemView.tv_catatan_note.text = listDataNote[position].catatan

//        HAPUS DATA
        holder.itemView.btn_delete_note.setOnClickListener {

            notedatabase = NoteDatabase.getInstance(it.context)

            val interfacedelete = LayoutInflater.from(it.context).inflate(R.layout.fragment_home2, null, false)

            val dialogdelete = AlertDialog.Builder(it.context)
                .setView(interfacedelete)
                .create()

            dialogdelete.btn_hapus_hapus_data.setOnClickListener {
                GlobalScope.async {
                    val result = notedatabase?.NoteDao()?.deleteNote(listDataNote[position])
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if(result != 0){
                            Toast.makeText(it.context, "Data ${listDataNote[position].judul} Berhasil Dihapus", Toast.LENGTH_LONG).show()
                            (holder.itemView.context as Home1Fragment).GetNote()
                        }else{
                            Toast.makeText(it.context, "Data ${listDataNote[position].judul} Gagal Dihapus", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            dialogdelete.show()
        }

//        UPDATE DATA
        holder.itemView.btn_update_note.setOnClickListener {
            val interfaceupdate = LayoutInflater.from(it.context).inflate(R.layout.fragment_home4, null, false)
            val dialogupdate = AlertDialog.Builder(it.context)
                .setView(interfaceupdate)
                .create()

            dialogupdate.btn_edit_data.setOnClickListener {
                val move = Intent(it.context,Home4Fragment::class.java)
                move.putExtra("DATA_NOTE", listDataNote[position])
                it.context.startActivity(move)
            }
        }
    }

    override fun getItemCount(): Int {
        return listDataNote.size
    }
}