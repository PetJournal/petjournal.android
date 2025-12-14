package com.soujunior.petjournal.ui.screensApp.screensPets.petBirthDateScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import com.soujunior.petjournal.ui.theme.ColorCustom
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Header(
    modifier: Modifier = Modifier,
    petName: String = "Bolinha",
    petGender: String = "Adotada",
) {
    Column(modifier = modifier) {
        CreateTitleAndImageLogo(
            spaceBetween = 15.sdp,
            spaceBetweenbreadcrumbAndTitle = 10.sdp,
            title = stringResource(id = R.string.info_menssage_pet_birth_date, petName),
            breadcrumbEnable = true,
            breadcrumbIndex = 3,
            styleTitle = MaterialTheme.typography.headlineLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        val text =
            buildAnnotatedString {
                val textStyle =
                    MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                        fontSize = 12.ssp,
                    )

                withStyle(style = textStyle) {
                    append(stringResource(id = R.string.adoption_info_message, petName, petGender))
                }
            }

        Text(
            modifier = Modifier.padding(horizontal = 16.sdp),
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start,
            color = if (isSystemInDarkTheme()) Color.White else ColorCustom.gray_200,
            fontSize = 12.ssp,
            fontWeight = FontWeight(400),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeader() {
    Header()
}
