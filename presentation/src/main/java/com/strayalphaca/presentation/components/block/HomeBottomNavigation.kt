package com.strayalphaca.presentation.components.block

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.atom.bottom_navigation_button.BottomNavigationButton
import com.strayalphaca.presentation.models.BottomNavigationItem

@Composable
fun HomeBottomNavigation(menuList : List<BottomNavigationItem>, onClick : (BottomNavigationItem) -> Unit, currentRoute : String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
    ) {
        for (menu in menuList) {
            BottomNavigationButton(
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                isSelect = currentRoute == menu.route,
                onClick = { onClick(menu) },
                iconId = menu.iconId
            )
        }
    }
}