package com.noemi.carsreloaded.screens.distance

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.noemi.carsreloaded.adapter.CarAdapter
import com.noemi.carsreloaded.base.BaseFragment
import com.noemi.carsreloded.R
import com.noemi.carsreloded.databinding.FragmentDistanceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.logger.KOIN_TAG

class FragmentDistance : BaseFragment<FragmentDistanceBinding>(R.layout.fragment_distance), LocationListener {

    private val distanceViewModel: DistanceViewModel by viewModel()
    private val carAdapter by lazy { CarAdapter() }

    private lateinit var locationManager: LocationManager
    private var location: Location? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDistanceBinding.inflate(layoutInflater, container, false)

        with(binding) {
            viewModel = distanceViewModel
            distanceRecycleView.adapter = carAdapter
            lifecycleOwner = this@FragmentDistance
        }

        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        activity?.let {
            if (ActivityCompat.checkSelfPermission(it, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(it, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
            ) {
                return binding.root
            }
        }

        locationManager.requestLocationUpdates(GPS_PROVIDER, 200, 1f, this)
        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 200, 1f, this)

        location = locationManager.getLastKnownLocation(GPS_PROVIDER)

        when (location == null) {
            true -> location = locationManager.getLastKnownLocation(NETWORK_PROVIDER)
            else -> location?.let { distanceViewModel.saveUserLocationCoordinates(it) }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        distanceViewModel.loadCars()
    }

    override fun onLocationChanged(location: Location) {
        Log.d(KOIN_TAG, "My location coordinates longitude - ${location.longitude}, latitude - ${location.latitude}")
    }
}