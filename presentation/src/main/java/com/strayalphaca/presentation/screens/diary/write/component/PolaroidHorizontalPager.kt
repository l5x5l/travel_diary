package com.strayalphaca.presentation.screens.diary.write.component

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalphaca.presentation.components.block.EmptyPolaroidView
import com.strayalphaca.presentation.components.block.PolaroidView
import com.strayalphaca.presentation.screens.diary.model.MediaFileInDiary
import com.strayalphaca.presentation.utils.thenIf
import com.strayalphaca.travel_diary.domain.file.model.FileType
import kotlin.math.absoluteValue
import kotlin.math.min

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun PolaroidHorizontalPager(
    imageFiles : List<MediaFileInDiary>,
    diaryDate: DiaryDate,
    onClickVideo : (Uri) -> Unit,
    onClickDeleteButton : ((Uri) -> Unit)?,
    onClickAddMedia : () -> Unit,
    enabled : Boolean,
    isTabletMode : Boolean
) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        pageCount = min(imageFiles.size + 1, 3),
        state = pagerState
    ) { index ->
        val target = imageFiles.getOrNull(index)
        if (target != null) {
            val isVideo = imageFiles[index].fileType == FileType.Video
            PolaroidView(
                modifier = Modifier.thenIf(isTabletMode) {
                    graphicsLayer {
                        val pageOffset = pagerState.run { currentPage - index + currentPageOffsetFraction }
                        if (pageOffset < 0) {
                            translationY = -pageOffset * size.height * 0.3f
                            translationX = pageOffset * size.width
                        }
                        alpha = 1 - pageOffset.absoluteValue
                    }
                },
                fileUri = imageFiles[index].uri,
                thumbnailUri = imageFiles[index].getThumbnailUriOrFileUri(),
                isVideo = isVideo,
                onClick = onClickVideo,
                onDeleteClick = onClickDeleteButton,
                dateString = diaryDate.toString(),
                positionString = "${index + 1}/${imageFiles.size}"
            )
        } else {
            EmptyPolaroidView(
                modifier = Modifier.thenIf(isTabletMode) {
                    graphicsLayer {
                        val pageOffset = pagerState.run { currentPage - index + currentPageOffsetFraction }
                        if (pageOffset < 0) {
                            translationY = -pageOffset * size.height * 0.3f
                            translationX = pageOffset * size.width
                        }
                        alpha = 1 - pageOffset.absoluteValue
                    }
                },
                onClick = {
                    if (!enabled) return@EmptyPolaroidView
                    onClickAddMedia()
                }
            )
        }
    }
}