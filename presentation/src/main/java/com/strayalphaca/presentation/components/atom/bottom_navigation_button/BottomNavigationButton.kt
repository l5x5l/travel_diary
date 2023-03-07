package com.strayalphaca.presentation.components.atom.bottom_navigation_button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationButton(
    modifier : Modifier = Modifier,
    isSelect : Boolean,
    onClick : () -> Unit,
    iconId : Int
) {
    val backgroundColor = if (isSelect) MaterialTheme.colors.onBackground else MaterialTheme.colors.background
    val tintColor = if (isSelect) MaterialTheme.colors.background else MaterialTheme.colors.onBackground

    Box(modifier = modifier.height(50.dp).background(backgroundColor).clickable { onClick() }, contentAlignment = Alignment.Center) {
        Icon(modifier = Modifier.size(30.dp), painter = painterResource(id = iconId), contentDescription = null, tint = tintColor)
    }
}

