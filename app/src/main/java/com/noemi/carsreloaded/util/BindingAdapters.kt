package com.noemi.carsreloaded.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.noemi.carsreloaded.adapter.CarAdapter
import com.noemi.carsreloaded.model.local.Car
import com.noemi.carsreloded.R

@BindingAdapter("isLoading")
fun bindProgressBarVisibility(view: CircularProgressIndicator, isLoading: Boolean) {
    view.isVisible = isLoading
}

@BindingAdapter("viewIsVisible")
fun bindViewVisibility(view: View, viewIsVisible: Boolean) {
    view.visibility = if (viewIsVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("cars")
fun bindViewVisibility(view: RecyclerView, cars: List<Car>?) {
    (view.adapter as CarAdapter).submitList(cars)
}

@BindingAdapter("carImage")
fun bindCarImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .error(R.drawable.jaguar)
        .placeholder(R.drawable.jaguar)
        .into(view)
}

@BindingAdapter("textValue")
fun bindCarText(view: TextView, car: Car) {
    view.text = view.context.getString(R.string.label_car_model, car.model.title, car.id, car.plateNumber)
}