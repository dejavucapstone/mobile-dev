package com.satria.gymer.ui.fragment.search

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satria.gymer.R
import com.satria.gymer.ui.activity.CameraXActivity
import com.satria.gymer.ui.adapter.ItemAdapter
import com.satria.gymer.ui.model.Item

class SearchFragment : Fragment() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openCameraActivity()
        } else {
            // Inform user that the permission is necessary
            requireContext().apply {
                Toast.makeText(this, "Izin kamera diperlukan untuk menggunakan fitur ini.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val items = listOf(
            Item(R.drawable.ic_launcher_foreground, "Item 1", "Subtitle 1", false),
            Item(R.drawable.ic_launcher_foreground, "Item 2", "Subtitle 2", true),
            Item(R.drawable.ic_launcher_foreground, "Item 3", "Subtitle 3", false)
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ItemAdapter(items) { item ->
            println("Item clicked: ${item.title}")
        }

        view.findViewById<ImageView>(R.id.ic_scan).setOnClickListener {
            checkCameraPermission()
        }

        return view
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                openCameraActivity()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // Show explanation dialog
                Toast.makeText(
                    requireContext(),
                    "Izin kamera diperlukan untuk menggunakan fitur ini.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCameraActivity() {
        val intent = Intent(requireContext(), CameraXActivity::class.java)
        startActivity(intent)
    }
}
