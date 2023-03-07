package com.strayalphaca.presentation.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun SettingsBaseScreen(
    exitSettingNav : () -> Unit = {},
    goToLogin : () -> Unit = {}
) {
    val navController : NavHostController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = settingsScreens.find { it.route == currentDestination?.route } ?: SettingsHomeScreenDestination

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            SettingsBaseScreenHeader(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = currentScreen.titleStringId),
                onBackButtonClick = {
                    if (currentScreen.route == SettingsHomeScreenDestination.route) {
                        exitSettingNav()
                    } else {
                        navController.popBackStack()
                    }
                }
            )

            SettingsNavHost(navController = navController, navigateToLogin = goToLogin)
        }
    }
}

@Composable
fun SettingsBaseScreenHeader(modifier : Modifier = Modifier, title : String, onBackButtonClick : () -> Unit) {
    Column(modifier = modifier) {
        BaseIconButton(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            iconResourceId = R.drawable.ic_close,
            onClick = onBackButtonClick
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(modifier = Modifier.padding(horizontal = 32.dp), text = title, style = MaterialTheme.typography.h2)

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(1.dp)
            .background(MaterialTheme.colors.onBackground))
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
fun SettingsBaseScreenPreview() {
    TravelDiaryTheme {
        Surface {
            SettingsBaseScreenHeader(title = "설정", onBackButtonClick = {})
        }
    }
}