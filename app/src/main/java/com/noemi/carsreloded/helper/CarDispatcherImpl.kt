package com.noemi.carsreloded.helper

import kotlinx.coroutines.Dispatchers

class CarDispatcherImpl : CarDispatcher {

    override fun dispatcher() = Dispatchers.IO
}