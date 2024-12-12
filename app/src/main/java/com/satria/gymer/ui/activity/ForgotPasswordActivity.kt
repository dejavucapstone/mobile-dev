package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password) // Pastikan layout ini benar

        // Mendapatkan referensi elemen UI
        val backIcon: ImageView = findViewById(R.id.backIcon)
        val emailInput: EditText = findViewById(R.id.emailInput)
        val sendEmailButton: ImageButton = findViewById(R.id.sendEmailButton) // Corrected here

        // Navigasi kembali ketika ikon "kembali" diklik
        backIcon.setOnClickListener {
            finish() // Menutup aktivitas saat tombol kembali diklik
        }

        // Logika tombol "Kirim Email"
        sendEmailButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isNotEmpty()) {
                if (isValidEmail(email)) {
                    // Menampilkan pesan bahwa email valid dan mengarahkan ke halaman verifikasi email
                    Toast.makeText(this, "Permintaan reset password terkirim ke $email", Toast.LENGTH_SHORT).show()

                    // Pindah ke EmailVerificationActivity
                    val intent = Intent(this, EmailVerificationActivity::class.java)
                    startActivity(intent)
                } else {
                    emailInput.error = "Masukkan email yang valid"
                }
            } else {
                // Menampilkan pesan kesalahan jika email kosong
                emailInput.error = "Email tidak boleh kosong"
            }
        }
    }

    // Fungsi untuk memvalidasi format email
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
