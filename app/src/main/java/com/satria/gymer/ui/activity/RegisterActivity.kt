package com.satria.gymer.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
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
<<<<<<< HEAD
import com.satria.gymer.utils.LoadingDialogUtils
import com.satria.gymer.utils.SharedPrefUtils
=======
import com.satria.gymer.network.ApiClient
import com.satria.gymer.network.ApiService
import com.satria.gymer.network.response.RegisterResp
import com.satria.gymer.network.response.ReqBodyRegister
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    @SuppressLint("SetTextI18n")
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

        // Setup spinner untuk jenis kelamin
        val jenisKelaminOptions = arrayOf("Laki-laki", "Perempuan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, jenisKelaminOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.jenisKelamin.adapter = adapter

        // Tombol Sign Up
        binding.signUpButton.setOnClickListener {
<<<<<<< HEAD
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
=======
            val firstName = binding.firstName.text.toString()
            val email = binding.emailRegister.text.toString()
            val phone = binding.phone.text.toString()
            val password = binding.passwordRegister.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            val jenisKelamin = binding.jenisKelamin.selectedItem.toString()
            val tinggiBadan = binding.tinggiBadan.text.toString().toFloatOrNull()
            val beratBadan = binding.beratBadan.text.toString().toFloatOrNull()

            // Validasi input
            if (firstName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || tinggiBadan == null || beratBadan == null) {
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
            } else if (!isPhoneValid(phone)) {
                binding.phoneError.visibility = View.VISIBLE
                binding.phoneError.text = "Nomor telepon tidak valid"
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8) {
                Toast.makeText(this, "Password harus lebih dari 8 karakter.", Toast.LENGTH_SHORT).show()
            } else {
<<<<<<< HEAD
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
=======
                // Kirim permintaan registrasi ke API
                val reqBody = ReqBodyRegister(
                    name = firstName,
                    email = email,
                    phone_number = phone,
                    password = password,
                    gender = jenisKelamin,
                    tinggi_badan = tinggiBadan,
                    berat_badan = beratBadan
                )
                registerUser(reqBody)
>>>>>>> 3edf99c5fb0c85679d8d267eca60229aeb8073d3
            }
        }

        // Tombol "Already Have Account"
        binding.signInLink.setOnClickListener {
            finish()
        }
    }

    // Fungsi untuk setup password toggle
    private fun setupPasswordToggle(editText: androidx.appcompat.widget.AppCompatEditText, toggleIcon: View) {
        toggleIcon.setOnClickListener {
            if (editText.transformationMethod is PasswordTransformationMethod) {
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                (toggleIcon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_eye_open)
            } else {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                (toggleIcon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_eye_closed)
            }
            editText.setSelection(editText.text?.length ?: 0)
        }
    }

    // Fungsi untuk validasi nomor telepon
    private fun isPhoneValid(phone: String): Boolean {
        val phonePattern = "^\\+?\\d{10,13}$" // Contoh regex untuk validasi nomor telepon
        return phone.matches(phonePattern.toRegex())
    }

    // Fungsi untuk melakukan request registrasi
    private fun registerUser(reqBody: ReqBodyRegister) {
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val call = apiService.registerUser(reqBody)

        call.enqueue(object : Callback<RegisterResp> {
            override fun onResponse(call: Call<RegisterResp>, response: Response<RegisterResp>) {
                if (response.isSuccessful && response.body() != null) {
                    val registerResp = response.body()
                    if (registerResp?.status == "success") {
                        Toast.makeText(this@RegisterActivity, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, registerResp?.message ?: "Gagal registrasi", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Menambahkan logging untuk error response
                    Toast.makeText(this@RegisterActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("RegisterActivity", "Response Error Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterResp>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
                Log.e("RegisterActivity", "Failure: ${t.message}")
            }
        })
    }
}
