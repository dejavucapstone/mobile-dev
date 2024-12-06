package com.satria.gymer.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_forgot_password)

        // Mendapatkan referensi elemen UI
        val backIcon: ImageView = findViewById(R.id.backIcon)
        val emailInput: EditText = findViewById(R.id.emailInput)
        val sendEmailButton: Button = findViewById(R.id.sendEmailButton)

        // Navigasi ketika ikon kembali diklik
        backIcon.setOnClickListener {
            finish() // Menutup aktivitas ini untuk kembali ke layar sebelumnya
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
    }
}
