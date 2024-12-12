package com.satria.gymer.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satria.gymer.R
import com.satria.gymer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toggle password visibility
        setupPasswordToggle(binding.password, binding.eyeIconPassword)

        // Handle Login Button
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill in all fields")
            } else if (password.length < 8) {
                showToast("Password must be at least 8 characters")
            } else {
                showToast("Login successful!")
                navigateToMainActivity()
            }
        }

        // Handle Forgot Password Button
        binding.forgotPasswordLink.setOnClickListener {
            navigateToForgotPasswordActivity()
        }

        // Handle Sign-Up Link
        binding.signUpLink.setOnClickListener {
            navigateToRegisterActivity()
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

    private fun navigateToForgotPasswordActivity() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun setupPasswordToggle(editText: androidx.appcompat.widget.AppCompatEditText, toggleIcon: View) {
        toggleIcon.setOnClickListener {
            if (editText.transformationMethod is PasswordTransformationMethod) {
                // Show password
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                (toggleIcon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_eye_open)
            } else {
                // Hide password
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                (toggleIcon as? android.widget.ImageView)?.setImageResource(R.drawable.ic_eye_closed)
            }
            // Keep cursor at the end of text
            editText.setSelection(editText.text?.length ?: 0)
        }
    }
}
