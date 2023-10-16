package com.strayalphaca.presentation.screens.settings.withdrawal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.login.use_case.UseCaseWithdrawal
import com.strayalphaca.domain.model.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val useCaseWithdrawal: UseCaseWithdrawal
) : ViewModel() {
    private val _totalDiaryCount = MutableStateFlow(0)
    val totalDiaryCount = _totalDiaryCount.asStateFlow()

    private val _initDataLoading = MutableStateFlow(true)
    val initDataLoading = _initDataLoading.asStateFlow()

    private val _deleteLoading = MutableStateFlow(false)
    val deleteLoading = _deleteLoading.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _withdrawalSuccess = MutableSharedFlow<Boolean>()
    val withdrawalSuccess = _withdrawalSuccess.asSharedFlow()

    private val _showCheckDialog = MutableStateFlow(false)
    val showCheckDialog = _showCheckDialog.asStateFlow()

    fun initDataLoading() {
        _initDataLoading.value = true
        viewModelScope.launch {
            delay(1000L)
            _initDataLoading.value = false
        }
    }

    fun withdrawal() {
        _initDataLoading.value = true
        viewModelScope.launch {
            val res = useCaseWithdrawal()
            if (res is BaseResponse.EmptySuccess) {
                _deleteLoading.value = false
                _withdrawalSuccess.emit(true)
            } else {
                val errorMessage = (res as BaseResponse.Failure).errorMessage
                _deleteLoading.value = false
                _toastMessage.emit(errorMessage)
            }
        }
    }

    fun showDialog() {
        _showCheckDialog.value = true
    }

    fun closeDialog() {
        _showCheckDialog.value = false
    }
}