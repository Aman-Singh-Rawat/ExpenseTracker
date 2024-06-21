package com.internship.expensetracker.presenter.screen.fragment.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.ExpenseAddBottomSheetFragmentBinding

class ExpenseAddBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: ExpenseAddBottomSheetFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExpenseAddBottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llCamera.setOnClickListener {
            handleCameraPermission()
        }

    }

    private val cameraPermissionRequestLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted: proceed with opening the camera
                startDefaultCamera()
            } else {
                // Permission denied: inform the user to enable it through settings
                Toast.makeText(
                    requireContext(),
                    "Go to settings and enable camera permission to use this feature",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    // Key Point 2: Camera Intent Launcher
    // Declare a launcher for taking a picture, handling the result of the camera app
    private val takePictureLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // This can be expanded to handle the result data
            Toast.makeText(requireContext(), "Photo taken", Toast.LENGTH_SHORT).show()
        }

    // Checks camera permission and either starts the camera directly or requests permission
    private fun handleCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted: start the camera
                startDefaultCamera()
            }

            else -> {
                // Permission is not granted: request it
                cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    // Starts the default camera app for taking a picture
    private fun startDefaultCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureLauncher.launch(cameraIntent)
        Toast.makeText(requireContext(), "Camera started", Toast.LENGTH_SHORT).show()
    }
}