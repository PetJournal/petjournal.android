package com.soujunior.petjournal.ui.appArea.home.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.appArea.home.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter

@ExperimentalPagerApi
@Composable
fun Screen(navController: NavController, viewModel: HomeScreenViewModel) {
    val showDropdownMenu = remember { mutableStateOf(false) }
    val taskState by viewModel.taskState.collectAsState()
    val name = remember { mutableStateOf(viewModel.name.value.firstName) }
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    name.value = viewModel.name.value.firstName
                }

                is ValidationEvent.Failed -> {
                    name.value = "falha ao obter nome"
                }
            }
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor( color = Color.Transparent, darkIcons = true )
    systemUiController.setNavigationBarColor(Color.Black)
    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            titleTopBar = stringResource(R.string.hello, name.value.capitalizeFirstLetter()),
            isLoading = taskState is TaskState.Loading,
            titleTopBarColor = MaterialTheme.colorScheme.scrim,
            titleTopBarAligh = Alignment.CenterStart,
            showActions = true,
            shadowBelowTopBar = 0.dp,
            showButtonToReturn = false,
            navigationUp = navController,
            showTopBar = true,
            actions = {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = stringResource(R.string.menu_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 16.dp)
                        .clickable {
                            showDropdownMenu.value = true
                        }
                )
                if (showDropdownMenu.value) {
                    DropdownMenu(
                        expanded = showDropdownMenu.value,
                        onDismissRequest = { showDropdownMenu.value = false },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showDropdownMenu.value = false
                                viewModel.logout()
                                navController.navigate("accountManager")
                            },
                            text = {
                                Text(text = stringResource(R.string.logout), fontSize = 18.sp)
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = stringResource(R.string.logout)
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
                        .padding(start = 16.dp, end = 16.dp),
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
                                text = stringResource(R.string.services),
                                modifier = Modifier.weight(0.8f),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Text(
                                text = stringResource(R.string.see_more),
                                modifier = Modifier.clickable { },
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelLarge
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
}