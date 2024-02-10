package com.strayalphaca.presentation.screens.diary.component.template

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DiaryViewTemplate(
    modifier : Modifier = Modifier,
    hideMediaArea : Boolean = false,
    dateTextView : @Composable ColumnScope.() -> Unit,
    locationView : @Composable ColumnScope.() -> Unit,
    weatherFeelingView : @Composable ColumnScope.() -> Unit,
    polaroidHorizontalPager : @Composable ColumnScope.(Boolean) -> Unit,
    textField : @Composable ColumnScope.() -> Unit,
    soundView : @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = modifier
    ) {
        if (maxWidth < 600.dp) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                dateTextView()

                Spacer(modifier = Modifier.height(16.dp))

                locationView()

                Spacer(modifier = Modifier.height(16.dp))

                weatherFeelingView()

                Spacer(modifier = Modifier.height(24.dp))

                polaroidHorizontalPager(false)

                Spacer(modifier = Modifier.height(24.dp))

                textField()

                Spacer(modifier = Modifier.height(24.dp))

                soundView()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    dateTextView()
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        locationView()
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        weatherFeelingView()
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxSize()) {
                    if (!hideMediaArea) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        ) {
                            polaroidHorizontalPager(true)

                            Spacer(modifier = Modifier.height(24.dp))

                            soundView()
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        textField()
                    }
                }

            }
        }
    }
}