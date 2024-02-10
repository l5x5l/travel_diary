package com.strayalphaca.presentation.screens.diary.write.component.template

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
inline fun DiaryWriteContentView(
    modifier : Modifier = Modifier,
    crossinline locationView : @Composable ColumnScope.() -> Unit,
    crossinline weatherFeelingView : @Composable ColumnScope.() -> Unit,
    crossinline polaroidHorizontalPager : @Composable ColumnScope.() -> Unit,
    crossinline textField : @Composable ColumnScope.() -> Unit,
    crossinline soundView : @Composable ColumnScope.() -> Unit
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
                locationView()

                Spacer(modifier = Modifier.height(16.dp))

                weatherFeelingView()

                Spacer(modifier = Modifier.height(24.dp))

                polaroidHorizontalPager()

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
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        locationView()

                        Spacer(modifier = Modifier.height(24.dp))

                        polaroidHorizontalPager()

                        Spacer(modifier = Modifier.height(24.dp))

                        soundView()
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        weatherFeelingView()

                        Spacer(modifier = Modifier.height(24.dp))

                        textField()
                    }
                }

            }
        }
    }
}