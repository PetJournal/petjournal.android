package com.soujunior.petjournal.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soujunior.petjournal.ui.components.data.TaskData
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TaskCard(
    taskData: TaskData,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.clip(RectangleShape)
    ) {
        Surface(
            shape = RoundedCornerShape(16.sdp),
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.sdp)
                .animateContentSize()
        ) {
            // Background icon when expanded - outside the Surface
            if (expanded) {
                Icon(
                    painter = painterResource(id = taskData.tipo.iconeVector!!),
                    contentDescription = "Ícone ${taskData.tipo.nome}",
                    tint = taskData.tipo.cor.copy(alpha = .5f),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 2.sdp, bottom = 42.sdp)
                        .size(150.sdp)
                        .offset(x = (-80).dp, y = (70).dp)
                )
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.sdp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.sdp)
                            .weight(0.4f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = taskData.titulo,
                            modifier = Modifier.padding(bottom = 2.sdp),
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 15.ssp

                        )
                        Text(
                            text = taskData.dataHora,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray,
                            fontSize = 10.ssp
                        )

                        /**
                         * Vai receber n ids de pets e vai criar a grid com as imagens dos respectivos pets
                         * */
                        if (expanded) {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxWidth(),
                                columns = GridCells.Fixed(3),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                items(taskData.pets.size) { index ->
                                    Box(modifier = Modifier.aspectRatio(1f)) {
                                        PetItem(modifier = Modifier, imageRes = "", name = "", onClick = {})
                                    }
                                }
                            }
                        }
                    }

                    // Right side: Description
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.sdp)
                            .weight(0.6f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        val displayText = if (!expanded && taskData.descricaoResumida.length > 50) {
                            taskData.descricaoResumida.take(50) + "..."
                        } else {
                            taskData.descricaoResumida
                        }

                        Text(
                            text = displayText,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(start = 8.sdp)
                        )

                        if (expanded) {
                            Text(
                                text = taskData.descricaoCompleta,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .padding(start = 8.sdp, top = 8.sdp)
                            )
                        }
                    }
                }

                // Expanded content
                if (expanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.sdp, top = 16.sdp),
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .width(100.sdp)
                                .height(25.sdp)
                                .align(Alignment.CenterHorizontally),
                            border = BorderStroke(
                                1.sdp, Color(0xFF959EA6)
                            ),
                            shape = RoundedCornerShape(50.sdp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)

                        ) {
                            // TODO: AJUSTAR A COR DO BOTÃO PARA A COR DA TAREFA
                            Text(
                                text = "Editar Tarefa",
                                fontSize = 10.ssp,
                                color = taskData.tipo.cor,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }
                }

                // Bottom section: "Ver Mais" / "Ver Menos"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.sdp)
                        .background(taskData.tipo.cor)
                        .clickable { expanded = !expanded },
                ) {
                    Text(
                        text = if (expanded) "Ver Menos" else "Ver Mais",
                        fontSize = 10.ssp,
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(y = -6.sdp)
                    )
                }
            }
        }


    }
}

@Preview
@Composable
private fun TaskCardPreview() {
    Column(Modifier.padding(8.sdp)) {
        TaskCard(
            taskData = TaskFakeData.sampleTasks[0]
        )
        TaskCard(
            taskData = TaskFakeData.sampleTasks[1]
        )
        TaskCard(
            taskData = TaskFakeData.sampleTasks[2]
        )
    }
}

