package com.satria.gymer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.satria.gymer.R

class FragmentLogin : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Tambahkan logika untuk navigasi ke Forgot Password
        val forgotPasswordText: TextView = view.findViewById(R.id.textForgotPassword)
        forgotPasswordText.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_forgotPassword)
        }

        return view
    }
}
