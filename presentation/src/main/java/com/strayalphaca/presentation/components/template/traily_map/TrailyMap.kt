package com.strayalphaca.presentation.components.template.traily_map

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.block.DiaryInMap
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.getMapImageRatioById
import com.strayalphaca.presentation.utils.getMapImageResourceById
import com.strayalphaca.presentation.utils.pxToDp
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationId
import com.strayalphaca.travel_diary.map.model.LocationType

@Composable
fun TrailyMap(
    modifier: Modifier = Modifier,
    locationId: Int?,
    locationDiaryList: List<LocationDiary>,
    onClickDiary: (Int) -> Unit
) {
    var imageSize by remember { mutableStateOf(IntSize.Zero) }
    var boxRatio by remember { mutableStateOf(2f) }
    val imageRatio = getMapImageRatioById(locationId)

    Box(
        modifier
            .onGloballyPositioned { layoutCoordinates ->
                boxRatio = layoutCoordinates.size.width / layoutCoordinates.size.height.toFloat()
            }
    ) {
        Box(
            Modifier
                .then(
                    if (boxRatio < imageRatio) { // 세로가 더 길다
                        Modifier.fillMaxWidth()
                    } else { // 가로가 더 길다
                        Modifier.fillMaxHeight()
                    }
                )
                .align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier
                    .then(
                        if (boxRatio < imageRatio) { // 세로가 더 길다
                            Modifier.fillMaxWidth()
                        } else { // 가로가 더 길다
                            Modifier.fillMaxHeight()
                        }
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        imageSize = layoutCoordinates.size
                    },
                painter = painterResource(id = getMapImageResourceById(locationId)),
                contentDescription = null,
                contentScale = if (boxRatio < 1) { ContentScale.FillWidth } else { ContentScale.FillHeight },
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
            )

            locationDiaryList.forEach { locationDiary ->
                DiaryInMap(
                    modifier = Modifier
                        .width(40.dp)
                        .offset(
                            x = getOffsetX(
                                locationDiary.location,
                                imageSize.width
                            ).pxToDp() - 20.dp,
                            y = getOffsetY(locationDiary.location, imageSize.height).pxToDp()
                        ),
                    onClick = onClickDiary,
                    thumbnailUrl = locationDiary.thumbnailUri,
                    id = locationDiary.location.id.id
                )
            }
        }
    }
}

fun getOffsetX(location: Location, totalWidth: Int): Int {
    return when (location.type) {
        LocationType.PROVINCE -> {
            (totalWidth * location.id.getProvincePosition().x).toInt()
        }

        LocationType.CITY_GROUP -> {
            (totalWidth * location.id.getCityGroupPosition().x).toInt()
        }

        else -> {
            0
        }
    }
}

fun getOffsetY(location: Location, totalHeight: Int): Int {
    return when (location.type) {
        LocationType.PROVINCE -> {
            (totalHeight * location.id.getProvincePosition().y).toInt()
        }

        LocationType.CITY_GROUP -> {
            (totalHeight * location.id.getCityGroupPosition().y).toInt()
        }

        else -> {
            0
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
@Composable
fun MapScreenPreview() {
    TravelDiaryTheme {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .height(500.dp)
        ) {
            Box(Modifier.fillMaxSize()
                .padding(16.dp)
                .border(width = 1.dp, color = MaterialTheme.colors.onSurface)
                .padding(16.dp)) {
                TrailyMap(
                    modifier = Modifier.fillMaxSize(),
                    locationId = null,
                    locationDiaryList = listOf(
                        LocationDiary("", Location(LocationId(2), "부산", LocationId(2))),
                        LocationDiary("", Location(LocationId(8), "세종", LocationId(8))),
                        LocationDiary("", Location(LocationId(1), "서울", LocationId(1))),
                        LocationDiary("", Location(LocationId(5), "대전", LocationId(5))),
                        LocationDiary("", Location(LocationId(3), "울산", LocationId(3))),
                    ),
                    onClickDiary = {})
            }

        }
    }
}