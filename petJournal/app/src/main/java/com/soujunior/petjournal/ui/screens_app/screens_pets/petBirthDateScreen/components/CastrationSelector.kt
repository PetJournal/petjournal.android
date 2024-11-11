package com.soujunior.petjournal.ui.screens_app.screens_pets.petBirthDateScreen.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.AlertText
import com.soujunior.petjournal.ui.components.RoundedSquare
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun CastrationSelector(
    selectedCastration: (Boolean?) -> Unit,
    clearSelection: () -> Boolean,
    textError: List<String>? = null,
    textNamePet: String = "Bolinha"
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.sdp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.pet_castration, textNamePet),
                    fontSize = 17.ssp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.sdp)
                )
            }

            CastrationButtons(selectedCastration, clearSelection, textError)
        })
}

@Composable
private fun CastrationButtons(
    selectedCastration: (Boolean?) -> Unit,
    clearSelection: () -> Boolean,
    textError: List<String>? = null,
) {

    var selectedItem by remember { mutableStateOf("") }
    if (clearSelection()) {
        selectedItem = ""
        selectedCastration(null)
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 2.sdp)
            .testTag("genderButtons_test"),
        horizontalArrangement = Arrangement.spacedBy(16.sdp)
    ) {
        RoundedSquare(
            text = stringResource(id = R.string.text_yes),
            colorText = if (selectedItem == "S") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant,
            isSelected = selectedItem == "S",
            size = 95.sdp,
            topLeftRadius = 32.sdp,
            topRightRadius = 32.sdp,
            bottomLeftRadius = 32.sdp,
            bottomRightRadius = 32.sdp,
            image = if (selectedItem == "S") painterResource(id = R.drawable.icon_solid_check_enable) else painterResource(
                id = R.drawable.icon_check
            ),
            selectedColor = if (selectedItem == "S") MaterialTheme.colorScheme.primary else Color.Transparent,


            colorBackground = Color.Transparent,
            onClick = {
                selectedItem = "S"
                selectedCastration(true)
            }
        )

        RoundedSquare(
            text = stringResource(id = R.string.text_no),
            colorText = if (selectedItem == "N") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
            isSelected = selectedItem == "N",
            size = 95.sdp,
            topLeftRadius = 32.sdp,
            topRightRadius = 32.sdp,
            bottomLeftRadius = 32.sdp,
            bottomRightRadius = 32.sdp,
            image = if (selectedItem == "N") painterResource(id = R.drawable.icon_solid_close_enable) else painterResource(
                id = R.drawable.icon_close
            ),
            selectedColor = if (selectedItem == "N") MaterialTheme.colorScheme.primary else Color.Transparent,
            colorBackground = Color.Transparent,
            onClick = {
                selectedItem = "N"
                selectedCastration(false)
            }
        )
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        if (textError != null) {
            textError.forEach {
                AlertText(textMessage = it, modifier = Modifier.padding(10.sdp))
            }
        } else {
            Text(
                text = stringResource(id = R.string.required_field),
                modifier = Modifier.padding(start = 2.sdp, top = 10.sdp),
                fontSize = 12.ssp,
                color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CastrationSelectorPreview() {
    CastrationSelector(
        selectedCastration = {},
        clearSelection = { true },
        textError = null,
    )
}