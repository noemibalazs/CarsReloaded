package com.noemi.carsreloaded.screens.cars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.noemi.carsreloaded.adapter.CarAdapter
import com.noemi.carsreloaded.base.BaseFragment
import com.noemi.carsreloded.R
import com.noemi.carsreloded.databinding.FragmentCarsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentCars : BaseFragment<FragmentCarsBinding>(R.layout.fragment_cars) {

    private val carViewModel: CarsViewModel by viewModel()
    private val adapter: CarAdapter by lazy { CarAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCarsBinding.inflate(layoutInflater, container, false)

        with(binding) {
            viewModel = carViewModel
            carsRecycleView.adapter = adapter
            lifecycleOwner = this@FragmentCars
        }

        carViewModel.loadCars()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}