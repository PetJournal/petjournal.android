package com.soujunior.petjournal.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@Composable
fun GlideImage(
    modifier: Modifier = Modifier,
    context: Context,
    url: String,
    scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER,
    onLoadingFinished: (Boolean) -> Unit = {},
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            ImageView(ctx).apply {
                this.scaleType = scaleType
                Glide.with(context)
                    .load(url)
                    .listener(
                        object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                onLoadingFinished(false)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                onLoadingFinished(true)
                                return false
                            }
                        },
                    )
                    .into(this)
            }
        },
        update = { /* O Glide gerencia o update internamente */ },
    )
}
