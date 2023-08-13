package com.strayalphaca.presentation.screens.diary_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.strayalphaca.domain.diary.model.DiaryItem
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.components.block.DiaryItemView
import com.strayalphaca.presentation.components.template.dialog.CityPickerDialog
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DiaryListScreenContainer(
    moveToDiary: (String) -> Unit,
    onBackPress : () -> Unit,
    initCityGroupId : Int,
    viewModel : DiaryListViewModel = viewModel()
) {
    val diaryList = viewModel.diaryPager.collectAsLazyPagingItems()
    val title by viewModel.locationTitle.collectAsState()
    var showCityPickerDialog by remember { mutableStateOf(false) }

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
        diaryList = diaryList
    )
}

@Composable
fun DiaryListScreen(
    moveToDiary : (String) -> Unit,
    onBackPress: () -> Unit,
    onMapButtonPress : () -> Unit = {},
    title : String,
    diaryList : LazyPagingItems<DiaryItem>,
) {
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                content = {
                    items(
                        count = diaryList.itemCount,
                        key = { diaryList[it]?.id ?: "empty_$it" }
                    ) {
                        DiaryItemView(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { id ->
                                moveToDiary(id)
                            },
                            imageUrl = diaryList[it]?.imageUrl,
                            id = diaryList[it]?.id ?: "",
                            leftText = diaryList[it]?.cityName ?: ""
                        )
                    }

                }
            )

            
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
    val flow = MutableStateFlow(PagingData.from(data))

    TravelDiaryTheme {
        DiaryListScreen(
            {}, {}, {}, "서울", flow.collectAsLazyPagingItems()
        )
    }
}