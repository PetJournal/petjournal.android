package com.soujunior.petjournal.ui.components

import androidx.compose.foundation.background
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
    modifierImage: Modifier = Modifier ,
    modifierTextTitle: Modifier = Modifier.padding(start = 8.dp),
    styleTitle: TextStyle = MaterialTheme.typography.displayMedium,
    spaceBetween: Dp = 0.dp,
    spaceBottom: Dp = 16.dp,
    textAlign: TextAlign? = null
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box( modifier = Modifier.fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                    clip = false
                )
            ){
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 30.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                ImageLogo(modifier = modifierImage)
            }
            }
            Spacer(modifier = Modifier.height(spaceBetween))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
            ) {
                Text(
                    text = title,
                    style = styleTitle,
                    modifier = modifierTextTitle,
                    color =  MaterialTheme.colorScheme.primary,
                    textAlign = textAlign
                )
            }
            Spacer(modifier = Modifier.height(spaceBottom))
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