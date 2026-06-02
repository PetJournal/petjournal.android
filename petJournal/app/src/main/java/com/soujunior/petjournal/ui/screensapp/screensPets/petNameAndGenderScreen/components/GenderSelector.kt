package com.soujunior.petjournal.ui.screensapp.screensPets.petNameAndGenderScreen.components

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

@Composable
fun GenderSelector(
    selectedGender: (String) -> Unit,
    clearSelection: () -> Boolean,
    textError: List<String>? = null,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.pet_gender),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(20.sdp),
                )
            }

            GenderButtons(selectedGender, clearSelection, textError)
        },
    )
}

@Composable
private fun GenderButtons(
    selectedGender: (String) -> Unit,
    clearSelection: () -> Boolean,
    textError: List<String>? = null,
) {
    var selectedItem by remember { mutableStateOf("") }
    if (clearSelection()) {
        selectedItem = ""
        selectedGender("")
    }

    Row(
        modifier = Modifier.padding(horizontal = 2.sdp).testTag("genderButtons_test"),
        horizontalArrangement = Arrangement.spacedBy(16.sdp),
    ) {
        RoundedSquare(
            text = stringResource(id = R.string.pet_gender_male),
            isSelected = selectedItem == "M",
            size = 100.sdp,
            topLeftRadius = 32.sdp,
            topRightRadius = 32.sdp,
            bottomLeftRadius = 32.sdp,
            bottomRightRadius = 32.sdp,
            image = painterResource(id = R.drawable.icone_macho),
            selectedColor = if (selectedItem == "M") MaterialTheme.colorScheme.primary else Color.Transparent,
            colorBackground = Color.Transparent,
            onClick = {
                selectedItem = "M"
                selectedGender("M")
            },
        )

        RoundedSquare(
            text = stringResource(id = R.string.pet_gender_female),
            isSelected = selectedItem == "F",
            size = 100.sdp,
            topLeftRadius = 32.sdp,
            topRightRadius = 32.sdp,
            bottomLeftRadius = 32.sdp,
            bottomRightRadius = 32.sdp,
            image = painterResource(id = R.drawable.icone_femea),
            selectedColor = if (selectedItem == "F") MaterialTheme.colorScheme.primary else Color.Transparent,
            colorBackground = Color.Transparent,
            onClick = {
                selectedItem = "F"
                selectedGender("F")
            },
        )
    }
    Row(modifier = Modifier.fillMaxWidth().padding(start = 16.sdp)) {
        if (textError != null) {
            textError.forEach {
                AlertText(textMessage = it, modifier = Modifier.padding(10.sdp))
            }
        } else {
            Text(
                text = stringResource(id = R.string.required_field),
                modifier = Modifier.padding(start = 2.sdp, top = 10.sdp),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenderSelectorPreview() {
    GenderSelector(
        selectedGender = {},
        clearSelection = { true },
        textError = null,
    )
}
