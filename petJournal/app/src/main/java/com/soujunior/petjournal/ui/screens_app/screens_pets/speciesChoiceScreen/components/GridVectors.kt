package com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.RoundedSquare
import com.soujunior.petjournal.ui.util.Constants.BIRD
import com.soujunior.petjournal.ui.util.Constants.CAT
import com.soujunior.petjournal.ui.util.Constants.DOG
import com.soujunior.petjournal.ui.util.Constants.FISH
import com.soujunior.petjournal.ui.util.Constants.REPTILE
import com.soujunior.petjournal.ui.util.Constants.RODENT
import com.soujunior.petjournal.ui.util.calcDefault

@Composable
fun GridVectors(
    selectedSpecies: (String) -> Unit,
    clearSelection: () -> Boolean
) {
    val defaultSize = 11
    var selectedItem by remember { mutableStateOf("") }

    if (clearSelection()) {
        selectedItem = ""
    }

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    selectedItem = DOG
                    selectedSpecies(DOG)
                }
                .border(
                    width = 2.dp,
                    color =
                    if (selectedItem == DOG) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.medium
                )
                .background(
                    color = if (selectedItem == REPTILE) Color.Transparent else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 8),
                    topLeftRadius = defaultSize.dp,
                    topRightRadius = defaultSize.dp,
                    bottomLeftRadius = defaultSize.dp,
                    bottomRightRadius = defaultSize.dp,
                    image = painterResource(id = R.drawable.specie_dog),
                )
                Text(
                    text = stringResource(R.string.dog),
                    color =
                    if (selectedItem == DOG) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    selectedItem = BIRD
                    selectedSpecies(BIRD)
                }
                .border(
                    width = 2.dp,
                    color =
                    if (selectedItem == BIRD) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.medium
                )
                .background(
                    color = if (selectedItem == REPTILE) Color.Transparent else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 8),
                    topLeftRadius = defaultSize.dp,
                    topRightRadius = defaultSize.dp,
                    bottomLeftRadius = defaultSize.dp,
                    bottomRightRadius = defaultSize.dp,
                    image = painterResource(id = R.drawable.specie_bird),
                )
                Text(
                    text = stringResource(R.string.bird),
                    color =
                    if (selectedItem == BIRD) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    selectedItem = CAT
                    selectedSpecies(CAT)
                }
                .border(
                    width = 2.dp,
                    color =
                    if (selectedItem == CAT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.medium
                )
                .background(
                    color = if (selectedItem == REPTILE) Color.Transparent else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 8),
                    topLeftRadius = defaultSize.dp,
                    topRightRadius = defaultSize.dp,
                    bottomLeftRadius = defaultSize.dp,
                    bottomRightRadius = defaultSize.dp,
                    image = painterResource(id = R.drawable.specie_cat),
                )
                Text(
                    text = stringResource(R.string.cat),
                    color =
                    if (selectedItem == CAT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    selectedItem = FISH
                    selectedSpecies(FISH)
                }
                .border(
                    width = 2.dp,
                    color =
                    if (selectedItem == FISH) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.medium
                )
                .background(
                    color = if (selectedItem == REPTILE) Color.Transparent else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 8),
                    topLeftRadius = defaultSize.dp,
                    topRightRadius = defaultSize.dp,
                    bottomLeftRadius = defaultSize.dp,
                    bottomRightRadius = defaultSize.dp,
                    image = painterResource(id = R.drawable.specie_fish),
                )
                Text(
                    text = stringResource(R.string.fish),
                    color =
                    if (selectedItem == FISH) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    selectedItem = REPTILE
                    selectedSpecies(REPTILE)

                }
                .background(
                    color = if (selectedItem == REPTILE) Color.Transparent else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    width = 2.dp,
                    color = if (selectedItem == REPTILE) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 8),
                    topLeftRadius = defaultSize.dp,
                    topRightRadius = defaultSize.dp,
                    bottomLeftRadius = defaultSize.dp,
                    bottomRightRadius = defaultSize.dp,
                    image = painterResource(id = R.drawable.specie_reptile),
                )
                Text(
                    text = stringResource(R.string.reptile),
                    color =
                    if (selectedItem == REPTILE) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    selectedItem = RODENT
                    selectedSpecies(RODENT)
                }
                .border(
                    width = 2.dp,
                    color =
                    if (selectedItem == RODENT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    shape = MaterialTheme.shapes.medium
                )
                .background(
                    color = if (selectedItem == REPTILE) Color.Transparent else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 8),
                    topLeftRadius = defaultSize.dp,
                    topRightRadius = defaultSize.dp,
                    bottomLeftRadius = defaultSize.dp,
                    bottomRightRadius = defaultSize.dp,
                    image = painterResource(id = R.drawable.specie_rodent),
                )
                Text(
                    text = stringResource(R.string.rodent),
                    color =
                    if (selectedItem == RODENT)  MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PrevGrid(){
    val isClearSpecies by remember { mutableStateOf(false) }
    GridVectors(selectedSpecies = {}, clearSelection = { isClearSpecies })
}