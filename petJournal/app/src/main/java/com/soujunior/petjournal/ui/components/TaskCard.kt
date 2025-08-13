package com.soujunior.petjournal.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlin.math.exp

@Composable
fun TaskCard() {
    var expanded by remember { mutableStateOf(true) }

    Surface(
        shape = RoundedCornerShape(8.sdp),
        tonalElevation = 2.dp,
        modifier = Modifier
            .padding(2.sdp)
            .animateContentSize() // Animates the size change
    ) {
        Column {
            // Main content: Title/Date on Start, Description on End
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.sdp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Left side: Title and Date
                Column(
                    modifier = Modifier.padding(horizontal = 8.sdp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Carprofeno",
                        modifier = Modifier.padding(bottom = 2.sdp),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 15.ssp

                    )
                    Text(
                        text = "12/08/2025 - 10:30",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray,
                        fontSize = 10.ssp
                    )
                }

                // Right side: Description
                Column(modifier = Modifier.padding(horizontal = 8.sdp),
                    horizontalAlignment = Alignment.Start){
                    Text(
                        text = "Anti-inflamatorio não esteroide para alivio da dor e inflamação",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(start = 8.sdp)
                    )

                    if(expanded){
                        Text(
                            text = "E mais um monte de coisa detalhada que vai aparecer quando expandir",
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
                           
                   ){
                       // TODO: AJUSTAR A COR DO BOTÃO PARA A COR DA TAREFA
                        Text(
                            text = "Editar Tarefa",
                            fontSize = 10.ssp,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineLarge
                        )
                   }
                }
            }


            // Bottom section: "Ver Mais" / "Ver Menos"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.error)
                    .clickable { expanded = !expanded } // Toggles the expanded state
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.sdp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (expanded) "Ver Menos" else "Ver Mais",
                        fontSize = 10.ssp,
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun TaskCardPreview() {
    // TODO: Fazer ele receber os seguintes dados: Titulo, data, descrição menor, descrição normal
    // TODO: Imagens dos pets, tipo de tarefa
    TaskCard()
}
