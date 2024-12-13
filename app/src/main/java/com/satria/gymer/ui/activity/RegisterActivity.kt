package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satria.gymer.R
import com.satria.gymer.data.model.ErrorResponse
import com.satria.gymer.data.model.login.LoginRequest
import com.satria.gymer.data.model.login.LoginResponse
import com.satria.gymer.data.model.register.RegisterRequest
import com.satria.gymer.data.model.register.RegisterResponse
import com.satria.gymer.data.network.ApiConfig
import com.satria.gymer.databinding.ActivityRegisterBinding
import com.satria.gymer.utils.LoadingDialogUtils
import com.satria.gymer.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol Back
        binding.backIcon.setOnClickListener { onBackPressed() }

        // Setup toggle untuk password
        setupPasswordToggle(binding.etPasswordRegister, binding.ivEyeIconPassword)
        setupPasswordToggle(binding.etConfirmPassword, binding.ivEyeIconConfirmPassword)

        val loadingDialog = LoadingDialogUtils(this)

        // Tombol Sign Up
        binding.signUpButton.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmailRegister.text.toString()
            val bodyWeight = binding.etBodyWeight.text.toString()
            val bodyHeight = binding.etBodyHeight.text.toString()
            val gender = when(binding.rgGender.checkedRadioButtonId){
                R.id.rb_male->"L"
                R.id.rb_female->"P"
                else->"-"
            }
            val password = binding.etPasswordRegister.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (firstName.isEmpty() || phone.isEmpty() || email.isEmpty() || bodyWeight.isEmpty() || bodyHeight.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8) {
                Toast.makeText(this, "Password harus lebih dari 8 karakter.", Toast.LENGTH_SHORT).show()
            } else {
                loadingDialog.show()
                val client = ApiConfig.getApiService().registerUser(
                    RegisterRequest(
                        email,
                        password,
                        firstName,
                        phone,
                        gender,
                        bodyWeight.toDouble(),
                        bodyHeight.toDouble()
                    )
                )
                client.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        loadingDialog.dismiss()
                        if (response.isSuccessful) {
                            response.body()?.let { loginResponse ->
                                Toast.makeText(this@RegisterActivity, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish() // Menutup RegisterActivity setelah berpindah
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            errorBody?.let {
                                try {
                                    val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                                    Toast.makeText(this@RegisterActivity, errorResponse.message, Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        loadingDialog.dismiss()
                    }
                })
            }
        }

        // Tombol "Already Have Account"
        binding.signInLink.setOnClickListener {
            finish()
        }
    }

    private fun setupPasswordToggle(editText: androidx.appcompat.widget.AppCompatEditText, toggleIcon: View) {
        toggleIcon.setOnClickListener {
            if (editText.transformationMethod is PasswordTransformationMethod) {
                // Tampilkan password
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                (toggleIcon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_eye_open)
            } else {
                // Sembunyikan password
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                (toggleIcon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_eye_closed)
            }
            editText.setSelection(editText.text?.length ?: 0)
        }
    }
}
