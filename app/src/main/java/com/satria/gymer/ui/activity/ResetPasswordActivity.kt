package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R
import com.satria.gymer.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigasi kembali ke ForgotPasswordActivity
        binding.backIcon.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent) // Menavigasi ke ForgotPasswordActivity
            finish() // Menutup ResetPasswordActivity setelah navigasi
        }

        // Logika visibilitas password
        setupPasswordVisibilityToggle()

        // Logika tombol konfirmasi
        binding.continueButton.setOnClickListener {
            val newPassword = binding.newPassword.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()

            when {
                newPassword.isEmpty() -> {
                    showToast("Password baru tidak boleh kosong.")
                }
                confirmPassword.isEmpty() -> {
                    showToast("Konfirmasi password tidak boleh kosong.")
                }
                newPassword != confirmPassword -> {
                    showToast("Password dan konfirmasi password tidak cocok.")
                }
                newPassword.length < 8 -> {
                    showToast("Password harus lebih dari 8 karakter.")
                }
                else -> {
                    showToast("Password berhasil diubah!")
                    // Menavigasi ke halaman login setelah password diubah
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Menutup ResetPasswordActivity setelah pindah ke halaman login
                }
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        // Toggle visibility for new password
        binding.toggleNewPasswordVisibility.setOnClickListener {
            togglePasswordVisibility(binding.newPassword, binding.toggleNewPasswordVisibility)
        }

        // Toggle visibility for confirm password
        binding.toggleConfirmPasswordVisibility.setOnClickListener {
            togglePasswordVisibility(binding.confirmPassword, binding.toggleConfirmPasswordVisibility)
        }
    }

    private fun togglePasswordVisibility(editText: EditText, toggleButton: ImageView) {
        val isPasswordVisible = editText.transformationMethod == null
        if (isPasswordVisible) {
            editText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            toggleButton.setImageResource(R.drawable.ic_eye_closed_black)
        } else {
            editText.transformationMethod = null
            toggleButton.setImageResource(R.drawable.ic_eye_open_black)
        }
        editText.setSelection(editText.text.length) // Move cursor to end
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
