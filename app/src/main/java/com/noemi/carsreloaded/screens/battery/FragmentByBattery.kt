package com.noemi.carsreloaded.screens.battery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noemi.carsreloaded.base.BaseFragment
import com.noemi.carsreloaded.adapter.CarAdapter
import com.noemi.carsreloaded.util.hideSoftKeyBoard
import com.noemi.carsreloded.R
import com.noemi.carsreloded.databinding.FragmentByBatteryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentByBattery : BaseFragment<FragmentByBatteryBinding>(R.layout.fragment_by_battery) {

    private val batteryViewModel: BatteryViewModel by viewModel()
    private val carAdapter by lazy { CarAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentByBatteryBinding.inflate(layoutInflater, container, false)

        with(binding) {
            viewModel = batteryViewModel
            batteryRecycleView.adapter = carAdapter
            lifecycleOwner = this@FragmentByBattery
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        batteryViewModel.hideKeyBoard.observe(viewLifecycleOwner) {
            if (it) activity?.hideSoftKeyBoard(binding.batteryPercentageEditText)
        }
    }
}