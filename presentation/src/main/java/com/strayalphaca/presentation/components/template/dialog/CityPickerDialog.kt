package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.map.model.City

@Composable
fun CityPickerDialog(
    onDismissRequest : () -> Unit = {},
    onLocationSelect : (locationId : Int?) -> Unit,
    cityGroupId : Int,
    currentSelectedLocationId : Int ?= null
) {
    var selectedLocation : Int? by remember { mutableStateOf(currentSelectedLocationId) }
    val cityList : List<City> by remember {
        mutableStateOf(City.getSameGroupCityList(cityGroupId))
    }

    TapeDialog(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 28.dp),
                text = stringResource(id = R.string.select_location),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface
            )

            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = 28.dp),
                columns = GridCells.Adaptive(minSize = 120.dp)
            ) {
                items(cityList) { city ->
                    Text(
                        modifier = Modifier
                            .background(
                                color = if (selectedLocation == city.id) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                            )
                            .clickable {
                                selectedLocation = if (selectedLocation == city.id) {
                                    null
                                } else {
                                    city.id
                                }
                            }
                            .padding(vertical = 8.dp)
                        ,
                        text = city.name,
                        style = MaterialTheme.typography.body1,
                        color = if (selectedLocation == city.id) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 12.dp, bottom = 12.dp)
            ) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colors.onSurface)
                }

                TextButton(
                    onClick = {
                        onLocationSelect(selectedLocation)
                        onDismissRequest()
                    }
                ) {
                    Text(text = stringResource(id = R.string.confirm), color = MaterialTheme.colors.onSurface)
                }
            }
        }
    }
}