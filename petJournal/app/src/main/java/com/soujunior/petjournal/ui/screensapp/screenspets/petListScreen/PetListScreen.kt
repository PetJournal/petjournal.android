package com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetItem
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TrailBack
import com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen.components.PetItemMore
import com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen.shimmerEffect
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getPetListViewModelForPreview(): PetListViewModel {
    return if (LocalInspectionMode.current) {
        FakePetListViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun PetListScreen(navController: NavController) {
    val viewModel: PetListViewModel = getPetListViewModelForPreview()
    val context = LocalContext.current
    val taskState by viewModel.taskState.collectAsState()
    val state = viewModel.state.collectAsState()

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
        modifier =
            Modifier
                .navigationBarsPadding()
                .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ScaffoldCustom(
            modifier = Modifier,
            titleTopBar = stringResource(R.string.my_pets),
            showButtonToReturn = false,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = { paddingValues ->
                if (taskState is TaskState.Loading) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .padding(top = 20.sdp, bottom = 20.sdp)
                                    .size(width = 200.sdp, height = 24.sdp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .shimmerEffect(),
                        )

                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            items(6) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Box(
                                        modifier =
                                            Modifier
                                                .size(108.sdp)
                                                .clip(RoundedCornerShape(16.sdp))
                                                .shimmerEffect(),
                                    )
                                    Box(
                                        modifier =
                                            Modifier
                                                .padding(top = 8.sdp)
                                                .size(width = 80.sdp, height = 16.sdp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .shimmerEffect(),
                                    )
                                    Spacer(Modifier.padding(bottom = 24.sdp))
                                }
                            }
                        }
                    }
                } else {
                    TrailBack()

                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.which_pet_do_you_want_to_see),
                            modifier = Modifier.padding(top = 20.sdp, bottom = 20.sdp),
                            style =
                                TextStyle(
                                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                    fontWeight = FontWeight.SemiBold,
                                ),
                            fontSize = 20.ssp,
                        )
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxWidth(),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            items(state.value.listPets) { pet ->
                                PetItem(
                                    imageRes =
                                        "https://img.freepik.com/free-vector/" +
                                            "construction-web-template-flat-" +
                                            "style_23-2147774304.jpg?" +
                                            "semt=ais_hybrid&w=740&q=80",
                                    name = pet.petName ?: "sem nome",
                                    onClick = {},
                                )
                            }
                            item {
                                PetItemMore(
                                    onClick = {
                                        navController.navigate("pets/registerPet")
                                    },
                                )
                            }
                        }
                    }
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PetListPrev() {
    val nav = rememberNavController()
    PetJournalTheme {
        PetListScreen(nav)
    }
}
