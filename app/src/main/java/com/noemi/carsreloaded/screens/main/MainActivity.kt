package com.noemi.carsreloaded.screens.main

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.AlertDialog
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.noemi.carsreloaded.util.PERMISSION_CODE
import com.noemi.carsreloded.R
import com.noemi.carsreloded.databinding.ActivityMainBinding
import org.koin.core.logger.KOIN_TAG

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.navHostFragment)
        setUpBottomNavigation()

        checkPermissionsAreGranted()
    }

    private fun setUpBottomNavigation() {
        with(binding.bottomNavigation) {
            setupWithNavController(navController)
            selectedItemId = R.id.fragmentCars
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, navController)
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun checkPermissionsAreGranted() {
        if (!isPermissionGranted(ACCESS_FINE_LOCATION) && !isPermissionGranted(ACCESS_COARSE_LOCATION))
            requestPermission()
    }

    private fun isPermissionGranted(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode == PERMISSION_CODE) {
            true -> when (grantResults.isNotEmpty() && grantResults[0] != PERMISSION_GRANTED && grantResults[1] != PERMISSION_GRANTED) {
                true -> informUserPermissionsAreNecessary()
                else -> Log.d(KOIN_TAG, "Permissions are granted.")
            }
            else -> Unit
        }
    }

    private fun informUserPermissionsAreNecessary() {
        AlertDialog.Builder(this)
            .setTitle(R.string.label_permission_title)
            .setMessage(R.string.label_permission_message)
            .setPositiveButton(R.string.label_ok) { d, _ ->
                d.dismiss()
                requestPermission()
            }
            .show()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), PERMISSION_CODE)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}