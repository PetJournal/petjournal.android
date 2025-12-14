package com.soujunior.petjournal.ui.components

import android.content.Context
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide

@Composable
fun GlideImage(
    modifier: Modifier = Modifier,
    context: Context,
    url: String,
    scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER,
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            ImageView(ctx).apply {
                post { this.scaleType = scaleType }
                Glide.with(context)
                    .load(url)
                    .into(this)
            }
        },
    )
}
