package com.noemi.carsreloaded.helper

import kotlinx.coroutines.Dispatchers

class CarDispatcherImpl : CarDispatcher {

    override fun dispatcher() = Dispatchers.IO
}