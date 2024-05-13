package com.soujunior.petjournal.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.content.Context

@Composable
fun GlideImage(
    context: Context,
    url: String
) {
    AndroidView(
        factory = { ctx ->
            ImageView(ctx).apply {
                Glide.with(context)
                    .load(url)
                    .into(this)
            }
        }
    )
}
