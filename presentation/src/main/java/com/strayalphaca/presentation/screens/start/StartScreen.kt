package com.strayalphaca.presentation.screens.start

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.collectLatestInScope

@Composable
fun StartScreenContainer(
    viewModel: StartViewModel,
    goToIntro : () -> Unit,
    goToHome : () -> Unit
) {
    val composeScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatestInScope(composeScope) { navDestination ->
            when (navDestination) {
                StartScreenNavDestination.LOGIN -> {
                    goToIntro()
                }
                StartScreenNavDestination.HOME -> {
                    goToHome()
                }
            }
        }
    }

    StartScreen()
}

@Composable
fun StartScreen() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 800)
                ) {
                    it
                },
                modifier = Modifier.background(MaterialTheme.colors.surface)
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(0.54f),
                    painter = painterResource(id = R.drawable.ic_logo_top),
                    contentDescription = "logoTop"
                )
            }

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_logo_middle),
                contentDescription = "logoTop"
            )

            Box(modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(400, delayMillis = 400)
                ),
                modifier = Modifier.background(MaterialTheme.colors.surface)
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_logo_bottom),
                    contentDescription = "logoTop"
                )
            }

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
fun StartScreenPreview() {
    TravelDiaryTheme() {
        StartScreen()
    }
}