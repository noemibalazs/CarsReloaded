package com.noemi.carsreloaded.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloded.databinding.ItemCarBinding

class CarAdapter : ListAdapter<Car, CarAdapter.CarViewHolder>(CarDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding: ItemCarBinding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class CarViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(carItem: Car) {
            binding.car = carItem
        }
    }

    class CarDiffUtil : DiffUtil.ItemCallback<Car>() {

        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean = oldItem == newItem
    }
}