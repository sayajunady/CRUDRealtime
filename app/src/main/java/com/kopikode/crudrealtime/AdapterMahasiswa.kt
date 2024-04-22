package com.kopikode.crudrealtime

import android.content.Context
import android.content.LocusId
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdapterMahasiswa( val mhsContext: Context, val layoutResId: Int, val mhsList: List<Mahasiswa>) :ArrayAdapter<Mahasiswa> (mhsContext,
    layoutResId, mhsList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       val layoutInflater: LayoutInflater = LayoutInflater.from(mhsContext)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tvNama : TextView = view.findViewById(R.id.tv_nama)
        val tvAlamat : TextView = view.findViewById(R.id.tv_alamat)

        val mahasiswa = mhsList[position]

        tvNama.text = mahasiswa.nama
        tvAlamat.text = mahasiswa.alamat

        return view
    }
}