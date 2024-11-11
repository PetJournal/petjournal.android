package com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.CreateTitleAndImageLogo
import ir.kaaveh.sdpcompose.sdp


@Composable
fun Header(species: String = "Gato") {

    if (species.isNotEmpty()) {
        CreateTitleAndImageLogo(
            spaceBetween = 15.sdp,
            spaceBetweenbreadcrumbAndTitle = 10.sdp,
            title = stringResource(id = R.string.wow),
            breadcrumbEnable = true,
            breadcrumbIndex = 0,
            titleSecundary = stringResource(id = R.string.register_message, species),
            styleTitle = MaterialTheme.typography.headlineLarge,
        )

    } else {
        CreateTitleAndImageLogo(
            spaceBetween = 20.sdp,
            spaceBetweenbreadcrumbAndTitle = 15.sdp,
            title = stringResource(id = R.string.wow),
            breadcrumbEnable = true,
            breadcrumbIndex = 0,
            titleSecundary = stringResource(id = R.string.register_message_others, species),
            styleTitle = MaterialTheme.typography.headlineLarge,
        )
    }
    /* Spacer(modifier = Modifier.padding(12.dp))

     val text = buildAnnotatedString {
         withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
             append(stringResource(id = R.string.wow))
         }
         append("\n")
         append("\n")
         withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
             append(stringResource(id = R.string.register_message, species))
         }
     }
     Breadcrumb(index = 0)
     Text(
         modifier = modifier,
         text = text,
         style = MaterialTheme.typography.headlineMedium,
         textAlign = TextAlign.Start,
         color = MaterialTheme.colorScheme.primary,
         fontSize = 20.sp,
         fontWeight = FontWeight.Bold
         )

     Spacer(modifier = Modifier.padding(bottom = 20.dp))*/
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeader() {
    Header(species = "Cachorro")
}