package com.noemi.carsreloded.helper

import kotlinx.coroutines.CoroutineDispatcher

interface CarDispatcher {

    fun dispatcher(): CoroutineDispatcher
}