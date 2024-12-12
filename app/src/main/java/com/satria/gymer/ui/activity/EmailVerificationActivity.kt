package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R
import com.satria.gymer.databinding.ActivityEmailVerificationBinding

class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigasi kembali
        binding.backIcon.setOnClickListener {
            finish()
        }

        // Fokus otomatis antar kotak OTP
        setupOtpFocus()

        // Logika tombol verifikasi
        binding.sendEmailVerify.setOnClickListener {
            val otp = binding.otpBox1.text.toString().trim() +
                    binding.otpBox2.text.toString().trim() +
                    binding.otpBox3.text.toString().trim() +
                    binding.otpBox4.text.toString().trim()

            when {
                otp.isEmpty() -> {
                    showToast("Kode OTP tidak boleh kosong.")
                }
                otp.length < 4 -> {
                    showToast("Masukkan semua 4 digit kode OTP.")
                }
                otp == "1234" -> { // Dummy OTP untuk pengujian
                    showToast("Verifikasi berhasil!")
                    val intent = Intent(this, ResetPasswordActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    showToast("Kode OTP salah. Silakan coba lagi.")
                }
            }
        }

        // Membuat tulisan "Resend Email" menjadi bold dan clickable
        val resendEmailTextView: TextView = findViewById(R.id.resendEmail)
        val text = "If you don't receive the code Resend Email"
        val spannableString = SpannableString(text)

        // Membuat bagian "Resend Email" menjadi bold
        val start = text.indexOf("Resend Email")
        val end = start + "Resend Email".length
        spannableString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), start, end, 0)

        // Menetapkan SpannableString pada TextView
        resendEmailTextView.text = spannableString

        // Menambahkan listener untuk bagian "Resend Email"
        resendEmailTextView.setOnClickListener {
            // Navigasi ke ForgotPasswordActivity ketika "Resend Email" diklik
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupOtpFocus() {
        val otpBoxes = listOf(binding.otpBox1, binding.otpBox2, binding.otpBox3, binding.otpBox4)
        otpBoxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && index < otpBoxes.size - 1) {
                        otpBoxes[index + 1].requestFocus()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
