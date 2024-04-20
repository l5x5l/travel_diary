package com.strayalphaca.presentation.screens.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.presentation.models.uri_handler.FileHandler
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.model.FileType
import com.strayalphaca.travel_diary.domain.file.usecase.UseCaseUploadFiles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val useCaseUploadFiles: UseCaseUploadFiles,
    private val fileHandler: FileHandler
) : ViewModel() {

    private val _screenState = MutableStateFlow<CameraScreenState>(CameraScreenState.Camera)
    val screenState : StateFlow<CameraScreenState> = _screenState.asStateFlow()

    private val _moveToBackStack = MutableEventFlow<String?>()
    val moveToBackStack = _moveToBackStack.asEventFlow()

    // 사진 확인 화면에서 이전 카메라 화면으로 돌아가는 경우
    fun moveToCameraState() {
        _screenState.update {
            if (it is CameraScreenState.Camera) { it }
            else { CameraScreenState.Camera }
        }
    }
    
    fun onBackPressed() {
        val currentScreenState = screenState.value

        when (currentScreenState) {
            is CameraScreenState.Camera -> {
                viewModelScope.launch {
                    _moveToBackStack.emit(null)
                }
            }
            is CameraScreenState.PhotoConfirmation -> {
                currentScreenState.bitmap.recycle()
                moveToCameraState()
            }
        }
    }

    // 사진 확인에서 확정 버튼 클릭
    fun confirmPhoto() {
        val currentState = screenState.value
        if (currentState !is CameraScreenState.PhotoConfirmation) return

        viewModelScope.launch {
            // bitmap 을 file로 변경
            val fileName = "${Calendar.getInstance().timeInMillis}.jpg"
            val file = fileHandler.bitmapToFile(currentState.bitmap, fileName) ?: return@launch

            // useCaseUploadFile 가 List<FileInfo>를 인자로 받기 때문에 이를 위한 작업
            val fileInfos = listOf(file).map { FileInfo(file, FileType.Image) }
            val response = useCaseUploadFiles(fileInfos)

            if (response is BaseResponse.Success<List<String>>) {
                if (response.data.isNotEmpty())
                    _moveToBackStack.emit(file.path)
            }
        }
    }

    // 카메라 화면에서 사진 촬영 클릭
    fun takePhoto(bitmap : Bitmap) {
        _screenState.update {
            CameraScreenState.PhotoConfirmation(bitmap)
        }
    }
}

sealed interface CameraScreenState {
    object Camera : CameraScreenState
    data class PhotoConfirmation(val bitmap: Bitmap) : CameraScreenState // bitmap이 아닐 수도?
}