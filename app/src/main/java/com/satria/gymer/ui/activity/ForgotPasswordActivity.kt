package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.forgotpassword.ForgotPasswordRequest
import com.satria.gymer.data.model.forgotpassword.ForgotPasswordResponse
import com.satria.gymer.data.model.register.RegisterRequest
import com.satria.gymer.data.model.register.RegisterResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityForgotPasswordBinding
import com.satria.gymer.databinding.ActivityRegisterBinding
import com.satria.gymer.utils.LoadingDialogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root) // Pastikan layout ini benar

        // Mendapatkan referensi elemen UI

        // Navigasi kembali ketika ikon "kembali" diklik
        binding.backIcon.setOnClickListener {
            finish() // Menutup aktivitas saat tombol kembali diklik
        }

        val loadingDialog = LoadingDialogUtils(this)

        // Logika tombol "Kirim Email"
        binding.sendEmailButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            if (email.isNotEmpty()) {
                if (isValidEmail(email)) {

                    loadingDialog.show()
                    val client = ApiConfig.getApiService().forgotPassword(
                        ForgotPasswordRequest(email)
                    )
                    client.enqueue(object : Callback<ForgotPasswordResponse> {
                        override fun onResponse(
                            call: Call<ForgotPasswordResponse>,
                            response: Response<ForgotPasswordResponse>
                        ) {
                            loadingDialog.dismiss()
                            if (response.isSuccessful) {
                                response.body()?.let { forgotResponse ->
                                    Toast.makeText(this@ForgotPasswordActivity, "Permintaan reset password terkirim ke $email", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java).putExtra("email",email)
                                    startActivity(intent)
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                errorBody?.let {
                                    try {
                                        val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                                        Toast.makeText(this@ForgotPasswordActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                            loadingDialog.dismiss()
                        }
                    })
                } else {
                    binding.emailInput.error = "Masukkan email yang valid"
                }
            } else {
                // Menampilkan pesan kesalahan jika email kosong
                binding.emailInput.error = "Email tidak boleh kosong"
            }
        }
    }

    // Fungsi untuk memvalidasi format email
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
