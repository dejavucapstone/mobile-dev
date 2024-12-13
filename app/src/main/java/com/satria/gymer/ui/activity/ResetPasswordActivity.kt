package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.forgotpassword.ForgotPasswordRequest
import com.satria.gymer.data.model.forgotpassword.ForgotPasswordResponse
import com.satria.gymer.data.model.resetpassword.ResetPasswordRequest
import com.satria.gymer.data.model.resetpassword.ResetPasswordResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityResetPasswordBinding
import com.satria.gymer.utils.LoadingDialogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val email = intent.getStringExtra("email")!!

        // Logika tombol konfirmasi
        binding.continueButton.setOnClickListener {
            val newPassword = binding.newPassword.text.toString().trim()
            val resetCode = binding.resetCode.text.toString().trim()

            when {
                newPassword.isEmpty() -> {
                    showToast("Password baru tidak boleh kosong.")
                }
                resetCode.isEmpty() -> {
                    showToast("Konfirmasi password tidak boleh kosong.")
                }
                newPassword.length < 8 -> {
                    showToast("Password harus lebih dari 8 karakter.")
                }
                else -> {
                    val loadingDialog = LoadingDialogUtils(this)

                    loadingDialog.show()
                    val client = ApiConfig.getApiService().resetPassword(
                        ResetPasswordRequest(email, resetCode, newPassword)
                    )
                    client.enqueue(object : Callback<ResetPasswordResponse> {
                        override fun onResponse(
                            call: Call<ResetPasswordResponse>,
                            response: Response<ResetPasswordResponse>
                        ) {
                            loadingDialog.dismiss()
                            if (response.isSuccessful) {
                                response.body()?.let { forgotResponse ->
                                    showToast("Password berhasil diubah!")
                                    // Menavigasi ke halaman login setelah password diubah
                                    val intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish() // Menutup ResetPasswordActivity setelah pindah ke halaman login
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                errorBody?.let {
                                    try {
                                        val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                                        Toast.makeText(this@ResetPasswordActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                            loadingDialog.dismiss()
                        }
                    })

                }
            }
        }
    }

    private fun setupPasswordVisibilityToggle() {
        // Toggle visibility for new password
        binding.toggleNewPasswordVisibility.setOnClickListener {
            togglePasswordVisibility(binding.newPassword, binding.toggleNewPasswordVisibility)
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
