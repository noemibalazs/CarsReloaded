package com.noemi.carsreloaded.screens.plate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noemi.carsreloaded.adapter.CarAdapter
import com.noemi.carsreloaded.base.BaseFragment
import com.noemi.carsreloaded.util.hideSoftKeyBoard
import com.noemi.carsreloded.R
import com.noemi.carsreloded.databinding.FragmentPlateNumberBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPlateNumber : BaseFragment<FragmentPlateNumberBinding>(R.layout.fragment_plate_number) {

    private val plateNumberViewModel: PlateNumberViewModel by viewModel()
    private val adapter: CarAdapter by lazy { CarAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlateNumberBinding.inflate(layoutInflater, container, false)

        with(binding) {
            viewModel = plateNumberViewModel
            plateNumberRecycleView.adapter = adapter
            lifecycleOwner = this@FragmentPlateNumber
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plateNumberViewModel.hideKeyBoard.observe(viewLifecycleOwner) {
            if (it) activity?.hideSoftKeyBoard(binding.plateNumberEditText)
        }
    }
}