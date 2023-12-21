package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    val defaultSize = 13
    var selectedItem by remember { mutableStateOf("") }

    if (clearSelection()) {
        selectedItem = ""
    }

    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color =
                    if (selectedItem == DOG) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp)
                .clickable {
                    selectedItem = DOG
                    selectedSpecies(DOG)
                },
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
                    material = Color.White,
                )
                Text(
                    text = "Cachorro",
                    color = if (selectedItem == DOG) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (selectedItem == BIRD) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp)
                .clickable {
                    selectedItem = BIRD
                    selectedSpecies(BIRD)
                },
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
                    material = Color.White,
                )
                Text(
                    text = "Pássaro",
                    color = if (selectedItem == BIRD) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (selectedItem == CAT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp)
                .clickable {
                    selectedItem = CAT
                    selectedSpecies(CAT)
                },
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
                    material = Color.White,
                )
                Text(
                    text = "Gato",
                    color = if (selectedItem == CAT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (selectedItem == FISH) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp)
                .clickable {
                    selectedItem = FISH
                    selectedSpecies(FISH)
                },
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
                    material = Color.White,
                )
                Text(
                    text = "Peixe",
                    color = if (selectedItem == FISH) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (selectedItem == REPTILE) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp)
                .clickable {
                    selectedItem = REPTILE
                    selectedSpecies(REPTILE)
                },
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
                    material = Color.White,
                )
                Text(
                    text = "Réptil",
                    color = if (selectedItem == REPTILE) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (selectedItem == RODENT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(bottom = 8.dp)
                .clickable {
                    selectedItem = RODENT
                    selectedSpecies(RODENT)
                },
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
                    material = Color.White,
                )
                Text(
                    text = "Roedor",
                    color = if (selectedItem == RODENT) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}