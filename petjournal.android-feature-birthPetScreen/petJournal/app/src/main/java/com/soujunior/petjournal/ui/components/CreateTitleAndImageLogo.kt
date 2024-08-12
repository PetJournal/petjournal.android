package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@Composable
fun CreateTitleAndImageLogo(
    title: String,
    modifierImage: Modifier = Modifier
        .size(width = 150.dp, height = 150.dp)
        .padding(top = 20.dp),
    modifierTextTitle: Modifier = Modifier.padding(start = 8.dp, top = 16.dp),
    styleTitle: TextStyle = MaterialTheme.typography.displayMedium,
    spaceTop: Dp = 16.dp,
    spaceBetween: Dp = 0.dp,
    spaceBottom: Dp = 16.dp,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    textAlign: TextAlign? = null
) {
    BoxWithConstraints {
        Column {
            Spacer(modifier = Modifier.height(spaceTop))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageLogo(modifier = modifierImage)
            }
            Spacer(modifier = Modifier.height(spaceBetween))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = title,
                    style = styleTitle,
                    modifier = modifierTextTitle,
                    color = if (isDarkMode) MaterialTheme.colorScheme.primary else Color.Unspecified,
                    textAlign = textAlign
                )
            }
            Spacer(modifier = Modifier.height(spaceBottom))
        }
    }
}

@Preview
@Composable
fun testeImage() {
    PetJournalTheme {
        CreateTitleAndImageLogo(
            title = stringResource(id = R.string.access_account),
            styleTitle = MaterialTheme.typography.displayLarge,
            modifierImage = Modifier
                .size(width = 200.dp, height = 200.dp)
                .padding(top = 20.dp),
            isDarkMode = true
        )
    }
}