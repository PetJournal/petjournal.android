package com.soujunior.petjournal.ui.components

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@Composable
fun ProfilePicturePicker(
    imageUrl: String? = null,
    onEditClick: () -> Unit = {},
    size: Dp = 100.dp,
) {
    var isError by remember(imageUrl) { mutableStateOf(imageUrl == null) }

    Box(
        modifier =
            Modifier
                .padding(size / 10)
                .size(size),
        contentAlignment = Alignment.Center,
    ) {
        if (isError) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(size)
                        .clip(RoundedCornerShape(100))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            GlideImage(
                modifier =
                    Modifier
                        .size(size)
                        .clip(CircleShape),
                context = LocalContext.current,
                url = imageUrl ?: "",
                scaleType = ImageView.ScaleType.CENTER_CROP,
                onLoadingFinished = { success ->
                    isError = !success
                },
            )
        }

        IconButton(
            onClick = onEditClick,
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (size / 10), y = (size / 10))
                    .size(size / 3f)
                    .background(Color.White, CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(size / 6f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePicturePickerPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(24.dp)) {
            ProfilePicturePicker(imageUrl = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePicturePickerPreviewWithImage() {
    val longUrl =
        "https://images.unsplash.com/photo-1596854372404-912a725a3d7c?" +
            "ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=80"
    PetJournalTheme {
        Surface(modifier = Modifier.padding(24.dp)) {
            ProfilePicturePicker(imageUrl = longUrl)
        }
    }
}
