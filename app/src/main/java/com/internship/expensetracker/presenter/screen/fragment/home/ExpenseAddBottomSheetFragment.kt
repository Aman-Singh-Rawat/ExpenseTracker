package com.internship.expensetracker.presenter.screen.fragment.home

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.expensetracker.databinding.ExpenseAddBottomSheetFragmentBinding
import com.internship.expensetracker.presenter.viewmodel.ExpenseAddViewModel

class ExpenseAddBottomSheetFragment : BottomSheetDialogFragment() {
    private var uri: Uri? = null
    private lateinit var binding: ExpenseAddBottomSheetFragmentBinding
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) openCamera()
        else
            Toast.makeText(requireContext(), "If you add image then permission required",
            Toast.LENGTH_LONG).show()
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()) {bitmap: Bitmap? ->

        if (bitmap != null) {
            val result = Bundle().apply {
                putString("data", bitmap.toString())
            }
            parentFragmentManager.setFragmentResult("dataFromSecond", result)
            findNavController().navigateUp()
        }

    }

    private fun openCamera() {
        takePictureLauncher.launch(null)
    }

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
            if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        binding.imgGallery.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) READ_MEDIA_IMAGES else READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted, launch the photo picker
                    launchNewPhotoPicker()
                }
                shouldShowRequestPermissionRationale(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) READ_MEDIA_IMAGES else READ_EXTERNAL_STORAGE
                ) -> {
                    // Show an explanation to the user
                    showPermissionRationaleDialog()
                }
                else -> {
                    // Request the permission
                    requestPermissionLauncher.launch(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) READ_MEDIA_IMAGES else READ_EXTERNAL_STORAGE
                    )
                }
            }
        }
    }

    private val newPhotoPiker = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            parentFragmentManager.setFragmentResult("dataFromSecond", bundleOf("data" to uri.toString()))
            //viewModel.addUserImage(uri.toString())
            dismiss()
        }
    }


    private fun launchNewPhotoPicker() {
        newPhotoPiker.launch("image/*")
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchNewPhotoPicker()
        } else {
            // Permission denied, show a message or take appropriate action
            showPermissionDeniedMessage()
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Required")
            .setMessage("This app needs access to your photos to select an image.")
            .setPositiveButton("OK") { _, _ ->
                requestPermissionLauncher.launch(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) READ_MEDIA_IMAGES else READ_EXTERNAL_STORAGE
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showPermissionDeniedMessage() {
        Toast.makeText(
            context,
            "Permission denied. You can grant it in app settings.",
            Toast.LENGTH_LONG
        ).show()
    }

// Rest of your code remains the same
}