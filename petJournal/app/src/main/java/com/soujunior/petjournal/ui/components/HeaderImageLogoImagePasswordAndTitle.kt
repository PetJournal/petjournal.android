package com.soujunior.petjournal.ui.components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun HeaderImageLogoImagePasswordAndTitle(
    title: String,
    subText: String = "",
    modifierImage: Modifier = Modifier,
    modifierTextTitle: Modifier = Modifier.padding(start = 8.dp),
    styleTitle: TextStyle = MaterialTheme.typography.displayMedium,
    spaceBetween: Dp = 0.sdp,
    textAlign: TextAlign? = null
) {
    val view = LocalView.current
    val cutoutInsets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view)

    val topPadding = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        with(LocalDensity.current) {
            (cutoutInsets.displayCutout?.safeInsetTop?.toDp() ?: 0.sdp) + 5.sdp
        }
    } else {
        10.dp
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.sdp,
                        shape = RoundedCornerShape(bottomStart = 8.sdp, bottomEnd = 8.sdp),
                        clip = false
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = topPadding, bottom = 3.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ImageLogo(modifier = modifierImage)
                }
            }
            Spacer(modifier = Modifier.height(spaceBetween))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.sdp),
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.sdp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_password_semi_circulo),
                        contentDescription = stringResource(R.string.content_description_image_password),
                        modifier = Modifier.fillMaxSize()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        repeat(3) {
                            Image(
                                painter = painterResource(id = R.drawable.image_password_asterisk),
                                contentDescription = stringResource(R.string.content_description_image_password),
                                modifier = Modifier
                                    .size(30.sdp)

                            )
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.sdp),
            ) {
                Text(
                    text = title,
                    style = styleTitle,
                    modifier = modifierTextTitle,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = textAlign,
                    fontSize = 20.ssp,
                    fontWeight = FontWeight(100)
                )
            }
            if (!subText.isEmpty()){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.sdp),
                ) {
                    androidx.compose.material.Text(
                        text = subText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TesteImages() {
    PetJournalTheme {
        HeaderImageLogoImagePasswordAndTitle(
            title = "Esqueceu a senha?",
            subText = "Redefina a sua senha em duas etapas!",
            styleTitle = MaterialTheme.typography.headlineLarge,
            modifierImage = Modifier
                .size(width = 200.dp, height = 200.dp)
                .padding(top = 20.dp),
        )
    }
}