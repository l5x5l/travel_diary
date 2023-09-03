package com.strayalphaca.presentation.screens.settings.withdrawal

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.utils.collectAsEffect
import com.strayalphaca.presentation.utils.restartApplication


@Composable
fun WithdrawalScreen(
    modifier : Modifier = Modifier,
    viewModel : WithdrawalViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.initDataLoading()
    }

    val localContext = LocalContext.current
    val diaryCount by viewModel.totalDiaryCount.collectAsStateWithLifecycle()
    val initLoading by viewModel.initDataLoading.collectAsStateWithLifecycle()
    val deleteLoading by viewModel.deleteLoading.collectAsStateWithLifecycle()
    
    viewModel.toastMessage.collectAsEffect(block = { message ->
        Toast.makeText(localContext, message, Toast.LENGTH_SHORT).show()
    })

    viewModel.withdrawalSuccess.collectAsEffect(block = { success ->
        if (success) {
            restartApplication(localContext)
        }
    })

    if (initLoading) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(40.dp),
                strokeWidth = 4.dp
            )
        }
    } else {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(style = MaterialTheme.typography.body1, text = stringResource(id = R.string.current_login))
                Text(style = MaterialTheme.typography.body1, text = "")
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(style = MaterialTheme.typography.body1, text = stringResource(id = R.string.my_diaries))
                Text(style = MaterialTheme.typography.body1, text = stringResource(id = R.string.total_count_format, diaryCount))
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.caution)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                style = MaterialTheme.typography.body2,
                text = stringResource(id = R.string.withdrawal_caution_message)
            )

            Spacer(modifier = Modifier.weight(1f))

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = stringResource(id = R.string.withdrawal),
                onClick = {
                    viewModel.withdrawal()
                },
                isLoading = deleteLoading
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }


}