package com.soujunior.petjournal.ui.components

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun PetItem(
    modifier: Modifier = Modifier,
    imageRes: String,
    name: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Surface(
            modifier =
                Modifier
                    .size(108.sdp),
            shape = RoundedCornerShape(16.sdp),
            onClick = onClick,
        ) {
            if (!imageRes.isEmpty()) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    context = LocalContext.current,
                    url = imageRes,
                    scaleType = ImageView.ScaleType.CENTER_CROP,
                )
            } else {
                // placeholder de imagem vazia
                Image(
                    painter = painterResource(id = R.drawable.image_pet_empty_selected),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop,
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
    PetItem(modifier = Modifier, imageRes = "", name = "", onClick = {})
}
