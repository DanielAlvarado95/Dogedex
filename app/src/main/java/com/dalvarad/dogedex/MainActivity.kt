package com.dalvarad.dogedex

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.dalvarad.dogedex.api.ApiServiceInterceptor
import com.dalvarad.dogedex.auth.LoginActivity
import com.dalvarad.dogedex.databinding.ActivityMainBinding
import com.dalvarad.dogedex.doglist.DogListActivity
import com.dalvarad.dogedex.models.User
import com.dalvarad.dogedex.settings.SettingsActivity
import com.google.common.util.concurrent.ListenableFuture

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                //abrir camara
                startCamera()
            } else {
                Toast.makeText(applicationContext, R.string.acept_permission_camera, Toast.LENGTH_LONG).show()
            }
        }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        } else {
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }


        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }

        requestCamerapermission()

    }
    @SuppressLint("SuspiciousIndentation")
    private fun startCamera(){
      val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)


            //TODO Cambiar esta linea por back camera el emulador no funciona con ella
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            cameraProvider.bindToLifecycle(
                this, cameraSelector, preview
            )
        }, ContextCompat.getMainExecutor(this))

    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))

    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun requestCamerapermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // abrir camara
                    startCamera()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    AlertDialog.Builder(this)
                        .setTitle("Aceptame por favor")
                        .setMessage("Acepta la camara por favor fierro")
                        .setPositiveButton(android.R.string.ok){
                            _,_  ->
                            requestPermissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                        .setNegativeButton(android.R.string.cancel){
                            _,_ ->

                        }.show()
                }
                else -> {
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
        } else {
            //opern camera
            startCamera()
        }
    }

}