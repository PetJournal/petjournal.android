package com.soujunior.petjournal.ui.appArea.homeScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalPagerApi
@Composable
fun Screen(navController: NavController) {
    val userName: String = "Gelson"
    val darkTheme = isSystemInDarkTheme()

    Scaffold(
        bottomBar = { NavigationBar(navController) },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 20.dp, top = 40.dp)
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Olá, ")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.W900,
                                    fontSize = 18.sp
                                )
                            ) {
                                append("$userName!")
                            }
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (darkTheme) MaterialTheme.colorScheme.primary else Color.Unspecified,
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .weight(0.5f),
                    )

                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 13.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "Menu",
                            tint = if (darkTheme) MaterialTheme.colorScheme.primary else Color.Unspecified,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                Carousel()

                Spacer(modifier = Modifier.padding(top = 16.dp))

                Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                    Text(
                        text = "Serviços",
                        modifier = Modifier.weight(0.8f),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (darkTheme) MaterialTheme.colorScheme.primary else Color.Unspecified
                    )
                    Text(
                        text = "Ver mais",
                        modifier = Modifier.clickable { },
                        color = if (darkTheme) MaterialTheme.colorScheme.primary else Color.Unspecified,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))

                Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                    Menu(navController)
                }

            }
        }
    )

}