package com.soujunior.petjournal.ui.appArea.homeScreen.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.appArea.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom

@ExperimentalPagerApi
@Composable
fun Screen(navController: NavController, viewModel: HomeScreenViewModel) {
    val showDropdownMenu = remember { mutableStateOf(false) }
    var name: String = ""
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Log.e("testar", "->"+viewModel.name.value.toString())
                    name = viewModel.name.value.firstName
                }
                is ValidationEvent.Failed -> {
                    name = "Pessoa bonita"
                    Log.e("testar", "Erro na obtenção do nome")
                }
            }
        }
    }
    ScaffoldCustom(
        titleTopBar = "Olá, ${name}!",
        titleTopBarColor = MaterialTheme.colorScheme.scrim,
        titleTopBarAligh = Alignment.CenterStart,
        shadowBelowTopBar = 0.dp,
        showButtonToReturn = false,
        navigationUp = navController,
        showTopBar = true,
        showMenu = true,
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 20.dp)
                    .clickable {
                        Log.e(TAG, "menu foi clicado")
                        showDropdownMenu.value = true
                    }
            )
            if (showDropdownMenu.value) {
                DropdownMenu(
                    expanded = showDropdownMenu.value,
                    onDismissRequest = { showDropdownMenu.value = false },
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            showDropdownMenu.value = false
                            Log.e(TAG, "Logout")
                        },
                        text = {
                            Text(text = "Logout", fontSize = 18.sp)
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Logout"
                            )
                        }
                    )
                }
            }
        },
        showBottomBarNavigation = true,
        bottomNavigationBar = { NavigationBar(navController) },
        contentToUse = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                contentPadding = it
            ) {
                item {
                    val carouselImages = viewModel.carouselImages
                    Carousel(imageIds = carouselImages)
                }
                item { Spacer(modifier = Modifier.padding(top = 16.dp)) }
                item {
                    Row {

                        Text(
                            text = "Serviços",
                            modifier = Modifier.weight(0.8f),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Ver mais",
                            modifier = Modifier.clickable { },
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                item { Spacer(modifier = Modifier.padding(top = 10.dp)) }
                item {
                        Menu(navController)
                }
            }
        })
}