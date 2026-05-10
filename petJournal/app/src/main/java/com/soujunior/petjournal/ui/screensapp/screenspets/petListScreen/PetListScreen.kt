package com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetItem
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TrailBack
import com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen.components.PetItemMore
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.util.shimmerEffect
import ir.kaaveh.sdpcompose.sdp
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetListScreen(navController: NavController) {
    val viewModel: PetListViewModel = getPetListViewModelForPreview()
    val context = LocalContext.current
    val taskState by viewModel.taskState.collectAsState()
    val state = viewModel.state.collectAsState()
    var expandedMenuPetId by remember { mutableStateOf<String?>(null) }

    val isRefreshing = taskState is TaskState.Loading
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = { viewModel.reload() },
        )

    if (!LocalInspectionMode.current) {
        LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
            viewModel.reload()
        }

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
                .background(color = MaterialTheme.colorScheme.onPrimary)
                .navigationBarsPadding()
                .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ScaffoldCustom(
            modifier =
                Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding(),
            titleTopBar = stringResource(R.string.my_pets),
            showButtonToReturn = false,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = {
                NavigationBar(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding().statusBarsPadding(),
                )
            },
            contentToUse = { paddingValues ->
                val showShimmer = taskState is TaskState.Loading && state.value.listPets.isEmpty()

                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState),
                ) {
                    if (showShimmer) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.onPrimary)
                                    .padding(paddingValues),
                            horizontalAlignment = CenterHorizontally,
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
                                modifier = Modifier.fillMaxSize(),
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
                            horizontalAlignment = CenterHorizontally,
                        ) {
                            Text(
                                text = stringResource(R.string.which_pet_do_you_want_to_see),
                                modifier = Modifier.padding(top = 20.sdp, bottom = 20.sdp),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                items(state.value.listPets) { pet ->
                                    Box(contentAlignment = Alignment.Center) {
                                        PetItem(
                                            imageRes = pet.image ?: "",
                                            name = pet.petName ?: "",
                                            onClick = {
                                                navController.navigate("pets/registerPet/${pet.idPet}")
                                            },
                                            onLongClick = {
                                                expandedMenuPetId = pet.idPet
                                            },
                                        )
                                        DropdownMenu(
                                            expanded = expandedMenuPetId == pet.idPet,
                                            onDismissRequest = { expandedMenuPetId = null },
                                        ) {
                                            DropdownMenuItem(
                                                text = { Text("Apagar") },
                                                onClick = {
                                                    expandedMenuPetId = null
                                                    pet.idPet?.let {
                                                        viewModel.deletePetById(it)
                                                    }
                                                },
                                            )
                                        }
                                    }
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
                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier =
                            Modifier
                                .align(Alignment.TopCenter)
                                .padding(paddingValues),
                    )
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
