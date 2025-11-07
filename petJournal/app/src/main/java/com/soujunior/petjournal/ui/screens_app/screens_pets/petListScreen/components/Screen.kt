package com.soujunior.petjournal.ui.screens_app.screens_pets.petListScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.domain.model.response.petList
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetItem
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.screens_app.screens_pets.petListScreen.FakePetListViewModel
import com.soujunior.petjournal.ui.screens_app.screens_pets.petListScreen.PetListViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.getViewModel

@Composable
fun getPetListViewModelForPreview(): PetListViewModel {
    return if (LocalInspectionMode.current) {
        FakePetListViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun Screen(navController: NavController) {
    val viewModel: PetListViewModel = getPetListViewModelForPreview()
    val context = LocalContext.current
    val taskState by viewModel.taskState.collectAsState()

    if (!LocalInspectionMode.current) {
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ValidationEvent.Success -> {
                        navController.popBackStack()
                        navController.navigate("")
                    }

                    is ValidationEvent.Failed -> {}
                }

            }
        }
    }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                    Image(
                        painter = painterResource(R.drawable.rastro),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .offset(y = 30.sdp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding(),
                        horizontalAlignment = CenterHorizontally,
                    )
                    {
                        Spacer(modifier = Modifier.height(123.sdp))
                        Text(
                            text = stringResource(R.string.which_pet_do_you_want_to_see),
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                fontWeight = FontWeight.SemiBold
                            ),
                            fontSize = 20.ssp,

                            )

                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(horizontal = 32.sdp)
                                .fillMaxWidth(),
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(top = 33.sdp, bottom = 16.sdp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            items(petList) { pet ->
                                PetItem(
                                    imageRes = pet.petImage ?: "",
                                    name = pet.petName ?: "",
                                    onClick = {}
                                )
                            }
                            item {
                                PetItemMore(onClick = {})
                            }

                        }

                    }
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun PetListPrev() {
    val nav = rememberNavController()
    PetJournalTheme {
        Screen(nav)
    }
}
