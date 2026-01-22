package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.domain.model.response.PetResponse
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.GlideImage
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreen.FakeHomeViewModel
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreen.HomeScreenViewModel
import com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2.components.Carousel
import com.soujunior.petjournal.ui.screensapp.screenspets.taskListScreen.TaskListScreen
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.util.capitalizeFirstLetter
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
private fun getHomeViewModelForPreview2(): HomeScreenViewModel {
    return if (LocalInspectionMode.current) {
        FakeHomeViewModel()
    } else {
        getViewModel()
    }
}

@ExperimentalPagerApi
@Composable
fun HomeScreen(navController: NavController) {
    val mockPets =
        listOf(
            PetResponse("Baleia", "link1"),
            PetResponse("Rex", "link2"),
            PetResponse("Um nome muito grande para testar o limite", "link3"),
        )

    val viewModel: HomeScreenViewModel = getHomeViewModelForPreview2()
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
    systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.Black)
    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            titleTopBar = stringResource(R.string.hello, name.value.capitalizeFirstLetter()),
            isLoading = taskState is TaskState.Loading,
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
                    modifier =
                        Modifier
                            .size(50.dp)
                            .padding(end = 16.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { showDropdownMenu.value = true },
                            ),
                )
                if (showDropdownMenu.value) {
                    DropdownMenu(
                        expanded = showDropdownMenu.value,
                        onDismissRequest = { showDropdownMenu.value = false },
                        modifier = Modifier.padding(end = 16.dp),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showDropdownMenu.value = false
                                viewModel.logout()
                                navController.navigate("account_manager")
                            },
                            text = {
                                Text(text = stringResource(R.string.logout), fontSize = 18.sp)
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = stringResource(R.string.logout),
                                )
                            },
                        )
                    }
                }
            },
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    contentPadding = it,
                ) {
                    item {
                        val carouselImages = viewModel.carouselImages
                        Carousel(imageIds = carouselImages)
                    }
                    item { Spacer(modifier = Modifier.padding(top = 16.dp)) }

                    item {
                        Text(
                            "Meus Pets",
                            fontSize = 12.ssp,
                            fontWeight = FontWeight.Bold,
                        )
                        PetList(pets = mockPets)
                    }
                    item {
                        val nav = rememberNavController()
                        TaskListScreen(nav)
                    }
                }
            },
        )
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

@Composable
fun PetList(
    pets: List<PetResponse>,
    onAddNewPet: () -> Unit = {},
) {
    if (pets.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Surface(
                modifier = Modifier.size(108.sdp),
                shape = RoundedCornerShape(16.sdp),
                color = Color(0xFFD9D9D9),
                onClick = onAddNewPet,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Ainda sem pet.",
                            textAlign = TextAlign.Center,
                            fontSize = 15.ssp,
                            color = Color.Black.copy(alpha = 0.5f),
                            lineHeight = 14.ssp,
                        )
                    }
                }
            }
        }
    } else {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.sdp),
            contentPadding = PaddingValues(horizontal = 0.sdp),
        ) {
            items(pets) { pet ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    val imageUrl = pet.petImage
                    val isLoading = remember { mutableStateOf(false) }
                    val hasError = remember { mutableStateOf(false) }

                    LaunchedEffect(imageUrl) {
                        isLoading.value = true
                        hasError.value = false
                    }

                    Surface(
                        modifier = Modifier.size(108.sdp),
                        shape = RoundedCornerShape(16.sdp),
                        color = Color(0xFFD9D9D9),
                    ) {
                        if (imageUrl.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        painter = rememberVectorPainter(image = Icons.Default.BrokenImage),
                                        contentDescription = "Pet ausente",
                                        modifier = Modifier.size(40.sdp),
                                        contentScale = ContentScale.Fit,
                                        alpha = 0.6f,
                                    )
                                }
                            }
                        } else {
                            Box(modifier = Modifier.fillMaxSize()) {
                                GlideImage(
                                    modifier = Modifier.fillMaxSize(),
                                    context = LocalContext.current,
                                    url = imageUrl,
                                    scaleType = ImageView.ScaleType.CENTER_CROP,
                                    onLoadingFinished = { success ->
                                        isLoading.value = false
                                        hasError.value = !success
                                    },
                                )

                                if (isLoading.value) {
                                    Box(
                                        modifier =
                                            Modifier
                                                .fillMaxSize()
                                                .background(Color(0xFFDED1D1)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.sdp),
                                            color = MaterialTheme.colorScheme.primary,
                                            strokeWidth = 2.sdp,
                                        )
                                    }
                                }

                                if (hasError.value && !isLoading.value) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Image(
                                            painter = rememberVectorPainter(image = Icons.Default.BrokenImage),
                                            contentDescription = "Erro ao carregar imagem",
                                            modifier = Modifier.size(48.sdp),
                                            contentScale = ContentScale.Fit,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

@Preview(showBackground = true, name = "Lista de Pets")
@Composable
private fun PreviewPetList2() {
    val mockPets = emptyList<PetResponse>()
    PetList(pets = mockPets)
}

@Preview(showBackground = true, name = "Lista de Pets")
@Composable
private fun PreviewPetList3() {
    val mockPets =
        listOf(
            PetResponse("Baleia", "url"),
        )
    PetList(pets = mockPets)
}
