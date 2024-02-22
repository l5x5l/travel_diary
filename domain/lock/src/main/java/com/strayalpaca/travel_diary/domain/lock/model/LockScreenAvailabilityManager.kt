package com.strayalpaca.travel_diary.domain.lock.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LockScreenAvailabilityManager @Inject constructor() {
    var lockScreenEnabled : Boolean = true

    fun disableLockScreen() {
        lockScreenEnabled = false
    }

    fun enableLockScreen() {
        lockScreenEnabled = true
    }
}