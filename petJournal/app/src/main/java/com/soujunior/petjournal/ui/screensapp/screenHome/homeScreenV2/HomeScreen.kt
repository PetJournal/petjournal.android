package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetList
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TaskCard
import com.soujunior.petjournal.ui.components.TaskListItemShimmer
import com.soujunior.petjournal.ui.components.bottomSheet.CategoryMenu
import com.soujunior.petjournal.ui.components.bottomSheet.MenuBottomSheet
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import com.soujunior.petjournal.ui.components.dialog.CardDialog
import com.soujunior.petjournal.ui.components.horizontalButtonList.HorizontalButtonList
import com.soujunior.petjournal.ui.model.TagOption
import com.soujunior.petjournal.ui.model.TaskData
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.components.Carousel
import com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen.components.TaskDateComponent
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.shimmerEffect
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalPagerApi
@Composable
fun HomeScreen(navController: NavController) {
    var showSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDeleteId by remember { mutableStateOf<Pair<String, String>?>(null) }
    val viewModel: HomeScreenViewModel = getCorrectViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { _ ->
        }

    LaunchedEffect(Unit) {
        viewModel.checkNotificationPermission {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val isRefreshing = state.isGlobalLoading
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = { viewModel.onEvent(HomeEvent.ReloadAll) },
        )

    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
    ) {
        ScaffoldCustom(
            containerColor = MaterialTheme.colorScheme.background,
            titleTopBar =
                if (state.hasErrorOnNameUser || state.nameUser.isEmpty()) {
                    stringResource(R.string.welcome)
                } else {
                    stringResource(
                        R.string.hello,
                        state.nameUser.replaceFirstChar { it.uppercaseChar() },
                    )
                },
            isLoading = state.isLoadingUserName,
            modifier =
                Modifier
                    .navigationBarsPadding()
                    .statusBarsPadding(),
            showActions = true,
            actions = {
                if (state.isSyncingBackground) {
                    RotatingLoadingIcon(
                        modifier = Modifier.padding(end = 8.dp),
                    )
                }
                HomeTopBarActions(onLogout = { viewModel.logout() })
            },
            shadowBelowTopBar = 0.dp,
            showButtonToReturn = false,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = {
                NavigationBar(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding(),
                )
            },
            contentToUse = { paddingValues ->
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding =
                            PaddingValues(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding() + 16.dp,
                                start = 16.dp,
                                end = 16.dp,
                            ),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        if (state.isLoadingListPet) {
                            item {
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .shimmerEffect(),
                                )
                            }
                        } else {
                            item { Carousel(imageIds = viewModel.carouselImages) }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }

                        item {
                            if (!state.isLoadingListPet) {
                                SectionHeader(
                                    title = stringResource(R.string.section_my_pets),
                                    showButton = true,
                                    onAddClick = {
                                        navController.navigate("home/registerPet")
                                    },
                                )
                            }
                            PetList(
                                pets = state.listPets,
                                showReloadButton = state.hasErrorOnListPets,
                                isLoading = state.isLoadingListPet,
                                onReload = { viewModel.onEvent(HomeEvent.ReloadListPet) },
                            )
                        }
                        if (state.isLoadingListTask) {
                            item {
                                TaskListItemShimmer()
                            }
                        } else {
                            if (state.listTaskData.isNullOrEmpty()) {
                                item {
                                    EmptyTaskSection(onClick = {
                                        navController.navigate("home/registerTaskScreen")
                                    })
                                }
                            } else {
                                state.listTaskData?.let { taskDataList: List<TaskData> ->
                                    item {
                                        SectionHeader(
                                            title = stringResource(R.string.section_next_tasks),
                                            showButton = true,
                                            onAddClick = {
                                                navController.navigate("home/registerTaskScreen")
                                            },
                                        )
                                    }
                                    items(items = taskDataList, key = { it.id }) { task ->
                                        TaskCard(
                                            taskData = task,
                                            enableSwipeToDelete = true,
                                            onDelete = {
                                                taskToDeleteId = Pair(task.id, task.schedulerId)
                                                showDeleteDialog = true
                                            },
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 8.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }

                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )

                    MenuBottomSheet(
                        isVisible = showSheet,
                        onDismiss = { showSheet = false },
                    ) {
                        CategoryMenu(
                            menuItems = state.menuItems,
                            onSelect = { itemSelecionado ->
                                println("Usuário escolheu: $itemSelecionado")
                                showSheet = false
                            },
                        )
                    }

                    if (showDeleteDialog) {
                        CardDialog(
                            title = stringResource(R.string.delete),
                            textTopButton = stringResource(id = R.string.cancel),
                            textCenterButton = stringResource(id = R.string.delete_only_this_task),
                            textFooterButton = stringResource(R.string.delete_all_these_task),
                            onButtonTopClick = { showDeleteDialog = false },
                            onButtonCenterClick = {
                                taskToDeleteId?.let { (id, _) ->
                                    viewModel.onEvent(HomeEvent.OnDeleteOnlyThisTask(id))
                                }
                                showDeleteDialog = false
                            },
                            onButtonFooterClick = {
                                taskToDeleteId?.let { (_, schedulerId) ->
                                    viewModel.onEvent(HomeEvent.OnDeleteAllTheseTask(schedulerId))
                                }
                                showDeleteDialog = false
                            },
                        )
                    }
                }
            },
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
private fun getCorrectViewModel(): HomeScreenViewModel {
    return if (LocalInspectionMode.current) {
        FakeHomeViewModel()
    } else {
        getViewModel()
    }
}

@Composable
private fun SectionHeader(
    title: String,
    showButton: Boolean = false,
    onAddClick: () -> Unit = {},
    onDeleteTask: () -> Unit = {},
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.sdp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (showButton) {
            Surface(
                modifier =
                    Modifier
                        .clip(CircleShape)
                        .size(24.sdp)
                        .clickable(onClick = onAddClick),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.addpet),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.sdp),
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeTopBarActions(onLogout: () -> Unit) {
    val showDropdownMenu = remember { mutableStateOf(false) }

    Box {
        Icon(
            painter = painterResource(id = R.drawable.menu),
            contentDescription = stringResource(R.string.menu_description),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier =
                Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true),
                        onClick = { showDropdownMenu.value = true },
                    ),
        )
        DropdownMenu(
            expanded = showDropdownMenu.value,
            onDismissRequest = { showDropdownMenu.value = false },
            modifier = Modifier.padding(end = 16.dp),
        ) {
            DropdownMenuItem(
                onClick = {
                    showDropdownMenu.value = false
                    onLogout()
                },
                text = {
                    Text(
                        text = stringResource(R.string.logout),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = stringResource(R.string.logout),
                    )
                },
            )
        }
    }
}

@Composable
private fun EmptyTaskSection(onClick: () -> Unit = {}) {
    Row(modifier = Modifier.padding(top = 16.sdp)) {
        Column(modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(
                text = stringResource(R.string.no_tasks_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 16.sdp, bottom = 4.sdp),
            )
            Text(
                text = stringResource(R.string.no_tasks_subtitle),
                style = MaterialTheme.typography.bodyMedium,
            )
            Button2(
                text = stringResource(R.string.create_task_button),
                submit = onClick,
                enableButton = true,
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
            )
        }
        Column {
            Image(
                painter = painterResource(id = R.drawable.things_on_the_table),
                contentDescription = null,
                modifier = Modifier.size(180.dp),
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val nav = rememberNavController()
    PetJournalTheme {
        HomeScreen(nav)
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalButtonListPreview() {
    val menuItems =
        listOf(
            TagOption(
                id = "1",
                label = "Todos",
                icon = Icons.Default.Menu,
                color = Color.Blue,
            ),
            TagOption(
                id = "1",
                label = "Vacinas",
                icon = Icons.Default.Home,
                color = Color.Cyan,
            ),
        )
    MaterialTheme {
        HorizontalButtonList(onItemClick = {}, menuItems = menuItems, isLoading = true)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column {
        Text(
            text = "Próximas tarefas:",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.sdp),
        )
        TaskDateComponent(
            tasks = TaskFakeData.sampleTasks.take(3),
            onDeleteTask = {},
        )
    }
}

@Composable
fun RotatingLoadingIcon(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading_rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(1200, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
        label = "loading_angle",
    )
    Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = "Carregando dados da API...",
        tint = MaterialTheme.colorScheme.primary,
        modifier =
            modifier
                .size(24.dp)
                .graphicsLayer(rotationZ = angle),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SectionHeaderPreview() {
    MaterialTheme {
        Column {
            SectionHeader(
                title = "Meus Pets",
                showButton = true,
                onAddClick = {},
                onDeleteTask = {},
            )
        }
    }
}
