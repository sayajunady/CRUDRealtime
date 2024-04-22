package com.kopikode.crudrealtime

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etNama : EditText
    private lateinit var etAlamat : EditText
    private lateinit var btnSimpan : Button
    private lateinit var listMhs : ListView
    private lateinit var  ref : DatabaseReference
    private lateinit var mhsList : MutableList<Mahasiswa>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("mahasiswa")

        etNama = findViewById(R.id.etNama)
        etAlamat = findViewById(R.id.etAlamat)
        btnSimpan = findViewById(R.id.btnSimpan)
        listMhs = findViewById(R.id.lv_mhs)

        btnSimpan.setOnClickListener(this)

        mhsList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    mhsList.clear()
                    for (h in snapshot.children) {
                        val mahasiswa = h.getValue(Mahasiswa::class.java)
                        if (mahasiswa != null) {
                            mhsList.add(mahasiswa)
                        }
                    }
                }
                val adapter = AdapterMahasiswa(applicationContext, R.layout.item_mhs, mhsList)
                listMhs.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        }

    override fun onClick(v: View?) {
        saveData()
    }

    private fun saveData() {
        val nama: String = etNama.text.toString().trim()
        val alamat: String = etAlamat.text.toString().trim()

        if (nama.isEmpty()) {
            etNama.error = "Isi Nama!"
            return
        }

        if (alamat.isEmpty()) {
            etAlamat.error = "Isi Alamat"
            return

        }

        val mhsId = ref.push().key
        val mhs = Mahasiswa(mhsId, nama, alamat)
        if (mhsId != null) {
            ref.child(mhsId).setValue(mhs).addOnCompleteListener {
                Toast.makeText(applicationContext, "Data berhasil disimpan", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}