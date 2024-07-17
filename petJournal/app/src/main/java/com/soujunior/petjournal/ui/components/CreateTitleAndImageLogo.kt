package com.soujunior.petjournal.ui.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@Composable
fun CreateTitleAndImageLogo(
    title: String,
    modifierImage: Modifier = Modifier ,
    modifierTextTitle: Modifier = Modifier.padding(start = 8.dp),
    styleTitle: TextStyle = MaterialTheme.typography.displayMedium,
    spaceBetween: Dp = 0.dp,
    textAlign: TextAlign? = null
) {
    val view = LocalView.current
    val cutoutInsets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view)

    val topPadding = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        with(LocalDensity.current) {
            (cutoutInsets.displayCutout?.safeInsetTop?.toDp() ?: 10.dp) + 4.dp
        }
    } else {
        10.dp
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth().background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                        clip = false
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = topPadding, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ImageLogo(modifier = modifierImage)
                }
            }
            Spacer(modifier = Modifier.height(spaceBetween))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
            ) {
                Text(
                    text = title,
                    style = styleTitle,
                    modifier = modifierTextTitle,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = textAlign,
                    fontWeight = FontWeight(10)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TesteImage() {
    PetJournalTheme {
        CreateTitleAndImageLogo(
            title = stringResource(id = R.string.access_account),
            styleTitle = MaterialTheme.typography.displayLarge,
            modifierImage = Modifier
                .size(width = 200.dp, height = 200.dp)
                .padding(top = 20.dp),
        )
    }
}