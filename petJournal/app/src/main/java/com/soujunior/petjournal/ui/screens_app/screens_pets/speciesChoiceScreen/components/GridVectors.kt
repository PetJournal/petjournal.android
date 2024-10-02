package com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.soujunior.petjournal.ui.util.Constants.RACE_OTHER
import com.soujunior.petjournal.ui.util.Constants.REPTILE
import com.soujunior.petjournal.ui.util.Constants.RODENT
import com.soujunior.petjournal.ui.util.calcDefault
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun GridVectors(
    selectedSpecies: (String) -> Unit,
    clearSelection: () -> Boolean
) {
    val defaultSize = 11
    var selectedItem by remember { mutableStateOf("") }
    val colorBorder = MaterialTheme.colorScheme.outline
    if (clearSelection()) {
        selectedItem = ""
    }

    Row(
        modifier = Modifier
            .padding(vertical = 10.sdp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    selectedItem = DOG
                    selectedSpecies(DOG)
                }
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10.dp.toPx(), 10.dp.toPx(), 0f)
                        )
                    )
                    drawRoundRect(
                        color = if (selectedItem == DOG) Color.Transparent else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .border(
                    2.sdp,
                    if (selectedItem == DOG) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .padding(bottom = 8.sdp)
                .width(86.sdp)
                .height(90.sdp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 6),
                    topLeftRadius = defaultSize.sdp,
                    topRightRadius = defaultSize.sdp,
                    bottomLeftRadius = defaultSize.sdp,
                    bottomRightRadius = defaultSize.sdp,
                    image = painterResource(id = R.drawable.specie_dog),
                )
                Text(
                    text = stringResource(R.string.dog),
                    color =
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    selectedItem = BIRD
                    selectedSpecies(BIRD)
                }
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10.dp.toPx(), 10.dp.toPx(), 0f)
                        )
                    )
                    drawRoundRect(
                        color = if (selectedItem == BIRD) Color.Transparent else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .border(
                    2.sdp,
                    if (selectedItem == BIRD) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .padding(bottom = 8.sdp)
                .width(86.sdp)
                .height(90.sdp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 6),
                    topLeftRadius = defaultSize.sdp,
                    topRightRadius = defaultSize.sdp,
                    bottomLeftRadius = defaultSize.sdp,
                    bottomRightRadius = defaultSize.sdp,
                    image = painterResource(id = R.drawable.specie_bird),
                )
                Text(
                    text = stringResource(R.string.bird),
                    color =
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    selectedItem = CAT
                    selectedSpecies(CAT)
                }
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10.dp.toPx(), 10.dp.toPx(), 0f)
                        )
                    )
                    drawRoundRect(
                        color = if (selectedItem == CAT) Color.Transparent else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .border(
                    2.sdp,
                    if (selectedItem == CAT) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .padding(bottom = 8.sdp)
                .width(86.sdp)
                .height(90.sdp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 6),
                    topLeftRadius = defaultSize.sdp,
                    topRightRadius = defaultSize.sdp,
                    bottomLeftRadius = defaultSize.sdp,
                    bottomRightRadius = defaultSize.sdp,
                    image = painterResource(id = R.drawable.specie_cat),
                )
                Text(
                    text = stringResource(R.string.cat),
                    color =
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .padding(vertical = 10.sdp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    selectedItem = FISH
                    selectedSpecies(FISH)
                }
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10.dp.toPx(), 10.dp.toPx(), 0f)
                        )
                    )
                    drawRoundRect(
                        color = if (selectedItem == FISH) Color.Transparent else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .border(
                    2.dp,
                    if (selectedItem == FISH) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .padding(bottom = 8.sdp)
                .width(86.sdp)
                .height(90.sdp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 6),
                    topLeftRadius = defaultSize.sdp,
                    topRightRadius = defaultSize.sdp,
                    bottomLeftRadius = defaultSize.sdp,
                    bottomRightRadius = defaultSize.sdp,
                    image = painterResource(id = R.drawable.specie_fish),
                )
                Text(
                    text = stringResource(R.string.fish),
                    color =
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    selectedItem = REPTILE
                    selectedSpecies(REPTILE)
                }
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10.dp.toPx(), 10.dp.toPx(), 0f)
                        )
                    )
                    drawRoundRect(
                        color = if (selectedItem == REPTILE) Color.Transparent else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .border(
                    2.sdp,
                    if (selectedItem == REPTILE) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .padding(bottom = 8.sdp)
                .width(86.sdp)
                .height(90.sdp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 6),
                    topLeftRadius = defaultSize.sdp,
                    topRightRadius = defaultSize.sdp,
                    bottomLeftRadius = defaultSize.sdp,
                    bottomRightRadius = defaultSize.sdp,
                    image = painterResource(id = R.drawable.specie_reptile),
                )
                Text(
                    text = stringResource(R.string.reptile),
                    color =
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    selectedItem = RODENT
                    selectedSpecies(RODENT)
                }
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10.dp.toPx(), 10.dp.toPx(), 0f)
                        )
                    )
                    drawRoundRect(
                        color = if (selectedItem == RODENT) Color.Transparent else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .border(
                    2.sdp,
                    if (selectedItem == RODENT) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .padding(bottom = 8.sdp)
                .width(86.sdp)
                .height(90.sdp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedSquare(
                    size = calcDefault(defaultSize, 6),
                    topLeftRadius = defaultSize.sdp,
                    topRightRadius = defaultSize.sdp,
                    bottomLeftRadius = defaultSize.sdp,
                    bottomRightRadius = defaultSize.sdp,
                    image = painterResource(id = R.drawable.specie_rodent),
                )
                Text(
                    text = stringResource(R.string.rodent),
                    color =
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .padding(vertical = 10.sdp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    selectedItem = RACE_OTHER
                    selectedSpecies(RACE_OTHER)
                }
                .drawBehind {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(10.dp.toPx(), 10.dp.toPx(), 0f)
                        )
                    )
                    drawRoundRect(
                        color = if (selectedItem == RACE_OTHER) Color.Transparent else colorBorder,
                        style = stroke,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
                .border(
                    2.sdp,
                    if (selectedItem == RACE_OTHER) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.sdp)
                )
                .padding(bottom = 8.sdp)
                .width(86.sdp)
                .height(90.sdp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (selectedItem == RACE_OTHER) {

                    RoundedSquare(
                        size = calcDefault(defaultSize, 6),
                        topLeftRadius = defaultSize.sdp,
                        topRightRadius = defaultSize.sdp,
                        bottomLeftRadius = defaultSize.sdp,
                        bottomRightRadius = defaultSize.sdp,
                        image = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_question_white else R.drawable.ic_question_purple),
                    )
                } else {
                    RoundedSquare(
                        size = calcDefault(defaultSize, 6),
                        topLeftRadius = defaultSize.sdp,
                        topRightRadius = defaultSize.sdp,
                        bottomLeftRadius = defaultSize.sdp,
                        bottomRightRadius = defaultSize.sdp,
                        image = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_question_white else R.drawable.ic_question_light_purple)
                    )
                }
                Text(
                    text = stringResource(R.string.others),
                    color =
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.ssp
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PrevGrid() {
    val isClearSpecies by remember { mutableStateOf(false) }
    GridVectors(selectedSpecies = {}, clearSelection = { isClearSpecies })
}