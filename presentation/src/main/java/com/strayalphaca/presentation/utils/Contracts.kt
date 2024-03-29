package com.strayalphaca.presentation.utils

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

class GetMediaActivityResultContract : ActivityResultContracts.GetMultipleContents() {

    override fun createIntent(context: Context, input: String): Intent {
        return super.createIntent(context, input).apply {
            // Force only images and videos to be selectable
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        }
    }
}