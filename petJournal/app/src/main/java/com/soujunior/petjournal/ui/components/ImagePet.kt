package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import ir.kaaveh.sdpcompose.sdp


@Composable
fun ImagePet(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = modifier.weight(2f),
            horizontalAlignment = Alignment.End) {
            Box(
                modifier = modifier
                    .width(150.dp)
                    .height(153.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(size = 17.96.dp)
                    )
                    .clickable {
                        // TODO: Colocar função de abrir galeria e tirar foto
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle_150),
                    contentDescription = "image description",
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "edit icon",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

        }
        Column (modifier = modifier.weight(1f),
            horizontalAlignment = Alignment.End){
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(end = 24.sdp)
                    .clickable { },
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "delete icon",
                    contentScale = ContentScale.FillBounds
                )
            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = false, device = "id:pixel_4_xl")
@Composable
fun ImagePetPreview() {
    ImagePet()
}
