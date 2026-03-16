package com.soujunior.petjournal.ui.components

import android.widget.ImageView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetItem(
    modifier: Modifier = Modifier,
    imageRes: String,
    name: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .size(108.sdp)
                    .clip(RoundedCornerShape(16.sdp))
                    .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick,
                    ),
        ) {
            if (imageRes.isNotEmpty()) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    context = LocalContext.current,
                    url = imageRes,
                    scaleType = ImageView.ScaleType.CENTER_CROP,
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.icon_dog),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        Text(
            modifier = Modifier.padding(top = 2.sdp),
            text = if (name.length > 15) name.take(12) + "..." else name,
            fontSize = 16.ssp,
            maxLines = 1,
        )
        Spacer(Modifier.padding(bottom = 24.sdp))
    }
}

@Preview
@Composable
private fun previewPetItem() {
    PetItem(modifier = Modifier, imageRes = "", name = "", onClick = {}, onLongClick = {})
}
