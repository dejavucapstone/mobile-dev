package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R
import com.satria.gymer.databinding.ActivityLoginBinding
import com.satria.gymer.ui.fragment.FragmentForgotPassword

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toggle password visibility
        setupPasswordToggle(binding.password, binding.eyeIcon)

        // Handle Login Button
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill in all fields")
            } else {
                showToast("Login successful!")
                navigateToMainActivity()
            }
        }

        // Handle Forgot Password Button
        binding.forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, FragmentForgotPassword::class.java)
            startActivity(intent)
        }

        // Handle Sign-Up Link
        binding.signUpLink.setOnClickListener {
            navigateToRegisterActivity()
        }
    }

    private fun setupPasswordToggle(
        editText: androidx.appcompat.widget.AppCompatEditText,
        toggleIcon: android.view.View
    ) {
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
