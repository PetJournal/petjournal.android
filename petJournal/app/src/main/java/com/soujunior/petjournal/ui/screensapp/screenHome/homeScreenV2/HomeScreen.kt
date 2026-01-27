package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.domain.model.response.PetResponse
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetList
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TaskCard
import com.soujunior.petjournal.ui.components.bottomSheet.CategoryMenu
import com.soujunior.petjournal.ui.components.bottomSheet.MenuBottomSheet
import com.soujunior.petjournal.ui.components.data.TaskData
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import com.soujunior.petjournal.ui.components.horizontalButtonList.HorizontalButtonList
import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.components.Carousel
import com.soujunior.petjournal.ui.screensapp.screenspets.taskListScreen.components.TaskDateComponent
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.Constantes.allTagsId
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.getViewModel

@ExperimentalPagerApi
@Composable
fun HomeScreen(navController: NavController) {
    var showSheet by remember { mutableStateOf(false) }
    val viewModel: HomeScreenViewModel = getCorrectViewModel()
    viewModel.getData()

    val mockPets = emptyList<PetResponse>()

    val menuItems = viewModel.state.menuItems
    val tasks = emptyList<TaskData>()

    val taskState by viewModel.taskState.collectAsState()
    val name = remember { mutableStateOf(viewModel.name.value.firstName) }

    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
        systemUiController.setNavigationBarColor(Color.Black)
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> name.value = viewModel.name.value.firstName
                is ValidationEvent.Failed ->
                    name.value =
                        context.getString(R.string.error_fetching_name)
            }
        }
    }

    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            titleTopBar = stringResource(R.string.hello, name.value.capitalizeFirstLetter()),
            isLoading = taskState is TaskState.Loading,
            showActions = true,
            shadowBelowTopBar = 0.dp,
            showButtonToReturn = false,
            navigationUp = navController,
            showTopBar = true,
            actions = {
                HomeTopBarActions(
                    onLogout = {
                        viewModel.logout()
                        navController.navigate("account_manager")
                    },
                )
            },
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = { paddingValues ->
                Box(modifier = Modifier.fillMaxSize()) {
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
                        item {
                            Carousel(imageIds = viewModel.carouselImages)
                        }

                        item { Spacer(modifier = Modifier.padding(top = 16.dp)) }

                        item {
                            SectionHeader(title = stringResource(R.string.section_my_pets), showButton = true, onAddClick = {
                                navController.navigate("pets/registerPet")
                            })
                            PetList(pets = mockPets)
                        }

                        if (tasks.isEmpty()) {
                            item { EmptyTaskSection() }
                        } else {
                            item { SectionHeader(title = stringResource(R.string.section_next_tasks)) }
                            items(items = tasks, key = { it.id }) { task ->
                                TaskCard(
                                    taskData = task,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                )
                            }
                        }

                        item {
                            SectionHeader(title = stringResource(R.string.section_learn_more))
                            HorizontalButtonList(
                                onItemClick = {
                                    if (it == allTagsId) {
                                        showSheet = true
                                    } else {
                                        // navega para a rota em questao
                                    }
                                },
                                menuItems = menuItems,
                            )
                        }
                    }

                    MenuBottomSheet(
                        isVisible = showSheet,
                        onDismiss = { showSheet = false },
                    ) {
                        CategoryMenu(
                            menuItems = menuItems.subList(1, viewModel.state.menuItems.size),
                            onSelect = { itemSelecionado ->
                                println("Usuário escolheu: $itemSelecionado")
                                showSheet = false
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
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.ssp,
        )

        if (showButton)
            {
                Surface(
                    modifier =
                        Modifier
                            .size(24.sdp)
                            .clickable(onClick = onAddClick),
                    shape = CircleShape,
                    color = Color(0xFF8D4CD2),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.addpet),
                            tint = Color.White,
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
                    Text(text = stringResource(R.string.logout), fontSize = 18.sp)
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
private fun EmptyTaskSection() {
    Row(modifier = Modifier.padding(top = 16.sdp)) {
        Column(modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(
                text = stringResource(R.string.no_tasks_title),
                fontSize = 14.ssp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 16.sdp, bottom = 4.sdp),
            )
            Text(
                text = stringResource(R.string.no_tasks_subtitle),
                fontSize = 14.ssp,
            )
            Button2(
                text = stringResource(R.string.create_task_button),
                submit = { },
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
        HorizontalButtonList(onItemClick = {}, menuItems = menuItems)
    }
}

@Preview(showBackground = true, name = "Lista de Pets")
@Composable
private fun PreviewPetList() {
    val mockPets =
        listOf(
            PetResponse("Baleia", "url"),
            PetResponse("Rex", "url"),
            PetResponse("Nome Muito Longo de Pet", "url"),
        )
    PetList(pets = mockPets)
}

@Preview(showBackground = true, name = "Lista de Pets vazia")
@Composable
private fun PreviewPetList2() {
    val mockPets = emptyList<PetResponse>()
    PetList(pets = mockPets)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column {
        Text(
            text = "Próximas tarefas:",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.ssp,
            modifier = Modifier.padding(bottom = 8.sdp),
        )
        TaskDateComponent(
            tasks = TaskFakeData.sampleTasks.take(3),
        )
    }
}
