package com.strayalphaca.presentation.screens.diary_list

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.block.DiaryItemView
import com.strayalphaca.presentation.components.block.TapePolaroidView
import com.strayalphaca.presentation.components.template.dialog.CityPickerDialog
import com.strayalphaca.presentation.models.paging.SimplePagingState
import com.strayalphaca.presentation.ui.theme.Gray2
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.travel_diary.diary.model.DiaryItem

@Composable
fun DiaryListScreenContainer(
    moveToDiary: (String) -> Unit,
    onBackPress : () -> Unit,
    initCityGroupId : Int,
    viewModel : DiaryListViewModel = viewModel()
) {
    val title by viewModel.locationTitle.collectAsState()
    var showCityPickerDialog by remember { mutableStateOf(false) }

    val diaryPagingList = viewModel.diaryListSimplePaging.pagingData().collectAsState(emptyList())
    val diaryPagingState = viewModel.diaryListSimplePaging.pagingState().collectAsState(SimplePagingState.IDLE)

    LaunchedEffect(Unit) {
        viewModel.setCityGroup(initCityGroupId)
    }

    if (showCityPickerDialog) {
        CityPickerDialog(
            onDismissRequest = { showCityPickerDialog = false },
            onLocationSelect = viewModel::setCity,
            cityGroupId = initCityGroupId,
            currentSelectedLocationId = viewModel.selectedCityId
        )
    }

    DiaryListScreen(
        moveToDiary = moveToDiary,
        onBackPress = onBackPress,
        onMapButtonPress = { showCityPickerDialog = true },
        title = title,
        diaryList = diaryPagingList.value,
        pagingState = diaryPagingState.value,
        loadNext = viewModel::loadNext
    )
}

@Composable
fun DiaryListScreen(
    moveToDiary : (String) -> Unit,
    onBackPress: () -> Unit,
    onMapButtonPress : () -> Unit = {},
    title : String,
    diaryList : List<DiaryItem>,
    pagingState : SimplePagingState,
    loadNext : () -> Unit = {},
) {
    val lazyColumnListState = rememberLazyGridState()
    val startPaginate = remember {
        derivedStateOf {
            (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -6) >= lazyColumnListState.layoutInfo.totalItemsCount - 4
        }
    }
    val color = if (isSystemInDarkTheme()) Gray4 else Gray2

    LaunchedEffect(title) {
        lazyColumnListState.scrollToItem(0)
    }

    LaunchedEffect(startPaginate.value) {
        if (startPaginate.value && pagingState == SimplePagingState.IDLE) {
            loadNext()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)) {

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.h2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                BaseIconButton(
                    iconResourceId = R.drawable.ic_map,
                    onClick = {
                        onMapButtonPress()
                    }
                )

                BaseIconButton(
                    iconResourceId = R.drawable.ic_close,
                    onClick = {
                        onBackPress()
                    }
                )
            }

            Divider(
                modifier = Modifier.fillMaxWidth(), 
                color = MaterialTheme.colors.onSurface,
                thickness = 1.dp
            )

            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {

                when (pagingState) {
                    SimplePagingState.LOADING_INIT -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colors.onSurface,
                            strokeWidth = 2.dp
                        )
                    }
                    SimplePagingState.FAILURE_INIT -> {
                        TapePolaroidView(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .align(Alignment.Center)
                                .zIndex(4f),
                            textResourceId = R.string.location_error
                        )
                    }
                    else -> {
                        if (diaryList.isNotEmpty()) {
                            LazyVerticalGrid(
                                state = lazyColumnListState,
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                                content = {
                                    items(
                                        count = diaryList.size,
                                        key = { diaryList[it].id }
                                    ) {
                                        DiaryItemView(
                                            modifier = Modifier.fillMaxWidth(),
                                            onClick = { id ->
                                                moveToDiary(id)
                                            },
                                            imageUrl = diaryList[it].imageUrl,
                                            id = diaryList[it].id,
                                            leftText = diaryList[it].cityName
                                        )
                                    }

                                    item(span = { GridItemSpan(2)}) {
                                        when (pagingState) {
                                            SimplePagingState.LAST -> {
                                                Canvas(
                                                    modifier = Modifier
                                                        .align(Alignment.Center)
                                                        .size(48.dp)
                                                        .padding(16.dp),
                                                    onDraw = {
                                                        drawCircle(color)
                                                    }
                                                )
                                            }
                                            SimplePagingState.LOADING_NEXT -> {
                                                Box(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(48.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator(
                                                        modifier = Modifier
                                                            .size(24.dp),
                                                        color = MaterialTheme.colors.onSurface,
                                                        strokeWidth = 2.dp
                                                    )
                                                }
                                            }
                                            SimplePagingState.FAILURE_NEXT -> {

                                            }
                                            else -> {}
                                        }
                                    }
                                }
                            )
                        } else {
                            TapePolaroidView(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .align(Alignment.Center),
                                textResourceId = R.string.location_empty
                            )
                        }
                    }
                }

            }

            
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
fun DiaryListScreenPreview() {
    val data = listOf<DiaryItem>(
        DiaryItem("1", null, "서울"),
        DiaryItem("2", null, "부산"),
        DiaryItem("3", null, "울산"),
        DiaryItem("4", null, "인천"),
        DiaryItem("5", null, "광주"),
        DiaryItem("6", null, "대전"),
        DiaryItem("7", null, "대구"),
        DiaryItem("8", null, "세종"),
        DiaryItem("9", null, "서울"),
        DiaryItem("10", null, "서울"),
        DiaryItem("11", null, "서울"),
        DiaryItem("12", null, "서울")
    )

    TravelDiaryTheme {
        DiaryListScreen(
            {}, {}, {}, "서울", data, SimplePagingState.IDLE
        )
    }
}