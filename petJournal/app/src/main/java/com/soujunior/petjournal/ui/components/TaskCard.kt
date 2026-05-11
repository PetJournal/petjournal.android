package com.soujunior.petjournal.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import com.soujunior.petjournal.ui.model.TaskData
import com.soujunior.petjournal.ui.util.shimmerEffect
import com.soujunior.petjournal.ui.util.toCardFormat
import ir.kaaveh.sdpcompose.sdp

@Composable
fun TaskListItemShimmer() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        repeat(2) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerEffect(),
            )
        }
    }
}

@Composable
fun TaskCard(
    taskData: TaskData,
    modifier: Modifier = Modifier,
    expandValue: Boolean = false,
) {
    var expanded by rememberSaveable(taskData.id) { mutableStateOf(expandValue) }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RectangleShape),
    ) {
        Surface(
            shape = RoundedCornerShape(10.sdp),
            tonalElevation = 2.dp,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(2.sdp)
                    .animateContentSize(),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (expanded && taskData.type.iconVector != null) {
                    Icon(
                        painter = painterResource(id = taskData.type.iconVector!!),
                        contentDescription = "Ícone ${taskData.type.name}",
                        tint = taskData.type.color?.copy(alpha = .5f) ?: Color.Gray,
                        modifier =
                            Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 2.sdp, bottom = 42.sdp)
                                .size(150.sdp)
                                .offset(x = (-80).dp, y = (70).dp),
                    )
                }

                Column {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(2.sdp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .padding(horizontal = 8.sdp)
                                    .weight(0.4f),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text =
                                    if (!expanded && taskData.title.length > 12) {
                                        taskData.title.take(12).uppercase()
                                    } else {
                                        taskData.title.uppercase()
                                    },
                                modifier = Modifier.padding(bottom = 2.sdp),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = taskData.startAt.toCardFormat(),
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Gray,
                            )

                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.sdp))

                                val rows = taskData.pets.chunked(3)

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    rows.forEach { rowPets ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start,
                                        ) {
                                            rowPets.forEach { pet ->
                                                Box(
                                                    modifier =
                                                        Modifier
                                                            .weight(1f)
                                                            .aspectRatio(1f)
                                                            .padding(2.dp),
                                                ) {
                                                    PetItem(
                                                        modifier = Modifier,
                                                        imageRes = pet.image?: "",
                                                        name = pet.petName?: "",
                                                        onClick = {},
                                                    )
                                                }
                                            }
                                            val emptySlots = 3 - rowPets.size
                                            repeat(emptySlots) {
                                                Spacer(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Column(
                            modifier =
                                Modifier
                                    .padding(horizontal = 8.sdp)
                                    .weight(0.6f),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            val displayText =
                                if (!expanded && taskData.descriptionResumed.length > 50) {
                                    taskData.descriptionResumed.take(50) + "..."
                                } else {
                                    taskData.descriptionResumed
                                }

                            Text(
                                text = displayText,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier =
                                    Modifier
                                        .fillMaxWidth(1f)
                                        .padding(start = 8.sdp),
                            )

                            if (expanded) {
                                Text(
                                    text = taskData.descriptionCompleted,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(1f)
                                            .padding(start = 8.sdp, top = 8.sdp),
                                )
                            }
                        }
                    }
/** Botao de edição foi comentado ja que nao era usado **/
//                    if (expanded) {
//                        Column(
//                            modifier =
//                                Modifier
//                                    .fillMaxWidth()
//                                    .padding(bottom = 8.sdp, top = 16.sdp),
//                        ) {
//                            Button(
//                                onClick = {},
//                                modifier =
//                                    Modifier
//                                        .width(100.sdp)
//                                        .height(25.sdp)
//                                        .align(Alignment.CenterHorizontally),
//                                border = BorderStroke(1.sdp, Color(0xFF959EA6)),
//                                shape = RoundedCornerShape(50.sdp),
//                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
//                            ) {
//                                Text(
//                                    text = stringResource(R.string.edit_task),
//                                    style = MaterialTheme.typography.titleSmall,
//                                    color = taskData.type.color ?: Color.Red,
//                                )
//                            }
//                        }
//                    }

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(20.sdp)
                                .background(taskData.type.color ?: Color.Red)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = { expanded = !expanded },
                                ),
                    ) {
                        Text(
                            text =
                                if (expanded) {
                                    stringResource(R.string.show_minus)
                                } else {
                                    stringResource(
                                        R.string.show_more,
                                    )
                                },
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    Column(Modifier) {
        TaskCard(
            taskData = TaskFakeData.sampleTasks[0],
        )
    }
}

@Preview
@Composable
private fun TaskCardExpandedPreview() {
    Column(Modifier) {
        TaskCard(
            taskData = TaskFakeData.sampleTasks[0],
            expandValue = true,
        )
    }
}
