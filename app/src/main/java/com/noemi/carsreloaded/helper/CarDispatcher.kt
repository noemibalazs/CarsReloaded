package com.noemi.carsreloaded.helper

import kotlinx.coroutines.CoroutineDispatcher

interface CarDispatcher {

    fun dispatcher(): CoroutineDispatcher
}