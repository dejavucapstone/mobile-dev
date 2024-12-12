package com.satria.gymer.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.satria.gymer.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Set up button listeners
        val personalSettingButton: Button = view.findViewById(R.id.personal_setting)
        val notificationButton: Button = view.findViewById(R.id.notification)
        val privacySecurityButton: Button = view.findViewById(R.id.privacy_security)
        val promotionsButton: Button = view.findViewById(R.id.promotions)
        val membershipButton: Button = view.findViewById(R.id.membership)
        val paymentButton: Button = view.findViewById(R.id.payment)
        val aboutButton: Button = view.findViewById(R.id.about)
        val customerSupportButton: Button = view.findViewById(R.id.customer_support)
        val contactButton: Button = view.findViewById(R.id.contact)

        // Example of adding click listeners
        personalSettingButton.setOnClickListener {
            Toast.makeText(requireContext(), "Personal Setting clicked", Toast.LENGTH_SHORT).show()
        }

        notificationButton.setOnClickListener {
            Toast.makeText(requireContext(), "Notification clicked", Toast.LENGTH_SHORT).show()
        }

        privacySecurityButton.setOnClickListener {
            Toast.makeText(requireContext(), "Privacy and Security clicked", Toast.LENGTH_SHORT).show()
        }

        promotionsButton.setOnClickListener {
            Toast.makeText(requireContext(), "Promotions clicked", Toast.LENGTH_SHORT).show()
        }

        membershipButton.setOnClickListener {
            Toast.makeText(requireContext(), "Membership clicked", Toast.LENGTH_SHORT).show()
        }

        paymentButton.setOnClickListener {
            Toast.makeText(requireContext(), "Payment clicked", Toast.LENGTH_SHORT).show()
        }

        aboutButton.setOnClickListener {
            Toast.makeText(requireContext(), "About clicked", Toast.LENGTH_SHORT).show()
        }

        customerSupportButton.setOnClickListener {
            Toast.makeText(requireContext(), "Customer Support clicked", Toast.LENGTH_SHORT).show()
        }

        contactButton.setOnClickListener {
            Toast.makeText(requireContext(), "Contact clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}