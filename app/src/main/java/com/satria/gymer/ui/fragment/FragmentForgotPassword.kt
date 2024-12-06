package com.satria.gymer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.satria.gymer.R

class FragmentForgotPassword : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        // Mendapatkan referensi elemen UI
        val backIcon: ImageView = view.findViewById(R.id.backIcon)
        val emailInput: EditText = view.findViewById(R.id.emailInput)
        val sendEmailButton: Button = view.findViewById(R.id.sendEmailButton)

        // Navigasi ketika ikon kembali diklik
        backIcon.setOnClickListener {
            findNavController().navigateUp()  // Navigasi kembali
        }

        // Logika tombol kirim email
        sendEmailButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isNotEmpty()) {
                // Tambahkan logika untuk mengirim email atau proses lainnya
                // Misalnya, validasi email dan kirim permintaan reset password ke server
            } else {
                // Menampilkan pesan kesalahan jika email kosong
                emailInput.error = "Email tidak boleh kosong"
            }
        }

        return view
    }
}
