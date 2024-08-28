package com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen.IntroRegisterPetViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@Composable
fun Screen(navController: NavController) {
    val viewModel: IntroRegisterPetViewModel = getViewModel()
    val context = LocalContext.current
    val name by viewModel.name.collectAsState()
    val taskState by viewModel.taskState.collectAsState()

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.popBackStack()
                    navController.navigate("pets/speciesChoice")
                }

                is ValidationEvent.Failed -> {}
            }
        }
    }

    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            titleTopBar = stringResource(id = R.string.pet_registration),
            showButtonToReturn = true,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                if (taskState is TaskState.Loading)
                    IndeterminateCircularIndicator(modifier = Modifier.align(CenterHorizontally))
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    )
                    {

                        Header(name)

                        LazyColumn(
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(
                                    start = 16.sdp,
                                    end = 16.sdp,
                                    top = 25.sdp,
                                    bottom = it.calculateBottomPadding()
                                )
                                .background(MaterialTheme.colorScheme.background),
                            content = {
                                item { GridVectors() }
                                item {
                                    Button2(
                                        modifier = Modifier
                                            .width(200.sdp)
                                            .padding(bottom = 20.sdp),
                                        submit = {
                                            viewModel.setWasViewed()
                                        },
                                        enableButton = true,
                                        text = stringResource(id = R.string.text_continue)
                                    )
                                }
                            })
                    }
                }
            })
    }
}