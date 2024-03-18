package com.noemi.carsreloaded.screens.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.noemi.carsreloaded.base.BaseFragment
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloded.R
import com.noemi.carsreloded.databinding.FragmentMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.logger.KOIN_TAG

class FragmentMap : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private val mapViewModel: MapViewModel by viewModel()
    private val cars = mutableSetOf<Car>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)

        mapViewModel.loadCars()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMap()
        mapViewModel.cars.observe(viewLifecycleOwner) { cars.addAll(it) }
    }

    private fun setUpMap() {
        activity?.let { activity ->
            val googleApiAvailable = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(activity) == ConnectionResult.SUCCESS

            when (googleApiAvailable) {
                true -> {
                    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                    when (mapFragment == null) {
                        true -> {
                            val newMapFragment = SupportMapFragment.newInstance()
                            childFragmentManager.beginTransaction().replace(R.id.map, newMapFragment)
                                .commitAllowingStateLoss()
                            newMapFragment.getMapAsync(this)
                        }
                        else -> mapFragment.getMapAsync(this)
                    }
                }
                else -> Toast.makeText(activity, R.string.label_error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        try {
            val success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))
            if (success) Log.d(KOIN_TAG, "onMapReady() - map style set successfully.")
        } catch (e: Resources.NotFoundException) {
            Log.e(KOIN_TAG, "onMapReady() throws exception: ${e.message}")
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(54.66855, 25.23474), 12f))
        showCarsOnMap(cars.toList(), map)
    }

    private fun showCarsOnMap(cars: List<Car>, googleMap: GoogleMap) {
        cars.forEach { car ->
            val marker = googleMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        car.location.latitude,
                        car.location.longitude
                    )
                ).title(car.plateNumber)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
            marker?.showInfoWindow()
        }
    }
}