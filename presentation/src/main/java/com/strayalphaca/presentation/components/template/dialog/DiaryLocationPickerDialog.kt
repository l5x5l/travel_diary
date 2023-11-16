package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.Province

@Composable
fun DiaryLocationPickerDialog(
    onDismissRequest : () -> Unit = {},
    onCitySelect: (Int?) -> Unit,
    title : String = stringResource(id = R.string.select_location),
    message : String = stringResource(id = R.string.select_location_message)
) {
    var selectedProvinceId by remember { mutableStateOf<Int?>(null) }
    var selectedCityId by remember { mutableStateOf<Int?>(null) }
    var cityList by remember { mutableStateOf<List<City>>(emptyList()) }

    LaunchedEffect(selectedProvinceId) {
        selectedProvinceId?.let {
            selectedCityId = null
            cityList = City.getCityListInProvince(it)
        }
    }

    TapeDialog(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)) {

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(Province.getTotalProvinceList()) { province ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = if (selectedProvinceId == province.id) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                                )
                                .clickable {
                                    selectedProvinceId = province.id
                                }
                                .padding(vertical = 8.dp)
                            ,
                            text = province.name,
                            style = MaterialTheme.typography.body1,
                            color = if (selectedProvinceId == province.id) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cityList) { city ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = if (selectedCityId == city.id) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember{ MutableInteractionSource() }
                                ) {
                                    selectedCityId = if (selectedCityId == city.id) {
                                        null
                                    } else {
                                        city.id
                                    }
                                }
                                .padding(vertical = 8.dp)
                            ,
                            text = city.name,
                            style = MaterialTheme.typography.body1,
                            color = if (selectedCityId == city.id) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .align(Alignment.End)) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colors.onSurface)
                }

                TextButton(
                    onClick = {
                        onDismissRequest()
                        onCitySelect(selectedCityId)
                    }
                ) {
                    Text(text = stringResource(id = R.string.confirm), color = MaterialTheme.colors.onSurface)
                }
            }
        }
    }
}