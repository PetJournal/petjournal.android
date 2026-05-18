package com.soujunior.petjournal.ui.screensapp.screensPets.petDetailsScreenV2

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.GlideImage
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen.components.TaskDateComponent
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.theme.PetJournalTheme
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.util.getPetAgeInYears
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun getPetDetailsViewModelForPreview(): PetDetailsViewModel {
    return if (LocalInspectionMode.current) {
        FakePetDetailsViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun PetDetailsScreenV2(
    navController: NavController,
    petId: String? = null,
) {
    val viewModel: PetDetailsViewModel = getPetDetailsViewModelForPreview()
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val taskState by viewModel.taskState.collectAsState()

    val isInspectionMode = LocalInspectionMode.current

    LaunchedEffect(key1 = petId) {
        if (!isInspectionMode && petId != null) {
            viewModel.getPetDetails(petId)
        }
    }

    LaunchedEffect(key1 = context) {
        if (!isInspectionMode) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ValidationEvent.Success -> {}
                    is ValidationEvent.Failed -> {
                        navController.popBackStack()
                    }
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
            titleTopBar = "Perfil do Pet",
            showButtonToReturn = true,
            navigationUp = navController,
            floatingActionButton = {
            },
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
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                ) {
                    if (taskState is TaskState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    } else {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(
                                        top = paddingValues.calculateTopPadding() + 10.dp,
                                        bottom = paddingValues.calculateBottomPadding() + 16.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                    ),
                        ) {
                            state.pet?.let { currentPet ->
                                PetProfileHeader(
                                    petName = currentPet.petName ?: "",
                                    specie =
                                        currentPet.specieAlias ?: currentPet.specie?.name
                                            ?: "",
                                    gender = currentPet.gender ?: "-",
                                    breed = currentPet.breed?.name ?: "Sem raça",
                                    age = getPetAgeInYears(currentPet.dateOfBirth).toString(),
                                    weight = currentPet.size?.name ?: "-",
                                    imageUrl = currentPet.image,
                                    onEditClick = {
                                        navController.navigate("pets/registerPet/${currentPet.id}")
                                    },
                                )
                            }

                            Spacer(modifier = Modifier.height(24.sdp))

                            if (state.nextTasks.isNotEmpty()) {
                                Text(
                                    text = "Próximas tarefas:",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier =
                                        Modifier
                                            .padding(
                                                vertical = 8.sdp,
                                            ),
                                )

                                TaskDateComponent(
                                    tasks = state.nextTasks,
                                )
                            }
                        }
                    }
                }
            },
        )
    }
}

@Composable
fun PetProfileHeader(
    petName: String,
    specie: String,
    gender: String,
    breed: String,
    age: String,
    weight: String,
    imageUrl: String?,
    onEditClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.sdp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val isImageLoading = remember { mutableStateOf(false) }
        val hasError = remember { mutableStateOf(false) }

        LaunchedEffect(imageUrl) {
            isImageLoading.value = true
            hasError.value = false
        }

        // Verifica se é gato usando a string passada por parâmetro
        val isCat = specie.lowercase().contains("gato")
        val placeholderRes = if (isCat) R.drawable.cat_profile else R.drawable.dog_profile

        Surface(
            modifier = Modifier.size(130.sdp),
            shape = RoundedCornerShape(16.sdp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            if (imageUrl.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = placeholderRes),
                        contentDescription = "Placeholder do pet",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    GlideImage(
                        modifier = Modifier.fillMaxSize(),
                        context = LocalContext.current,
                        url = imageUrl,
                        scaleType = ImageView.ScaleType.CENTER_CROP,
                        onLoadingFinished = { success ->
                            isImageLoading.value = false
                            hasError.value = !success
                        },
                    )

                    if (isImageLoading.value) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.sdp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.sdp,
                            )
                        }
                    }

                    if (hasError.value && !isImageLoading.value) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = painterResource(id = placeholderRes),
                                contentDescription = "Erro ao carregar imagem",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }
        }

        Surface(
            modifier =
                Modifier
                    .weight(1f)
                    .height(130.sdp),
            shape = RoundedCornerShape(16.sdp),
            color = MaterialTheme.colorScheme.surfaceTint,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(12.sdp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(end = 32.sdp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text =
                            petName
                                .trim()
                                .substringBefore(" ")
                                .lowercase()
                                .replaceFirstChar { it.uppercase() }
                                .ifEmpty { "Sem Nome" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Text(
                        text = "${specie.ifEmpty { "-" }}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    gender?.let { g ->
                        val formattedGender =
                            when (g.uppercase()) {
                                "F" -> "Fêmea"
                                "M" -> "Macho"
                                else -> g
                            }

                        Text(
                            text = formattedGender,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Text(
                        text = breed,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Text(
                        text = "$age anos",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    weight?.takeIf { it.isNotBlank() }?.let { validWeight ->
                        val formattedWeight =
                            if (validWeight.contains("(")) {
                                val parts = validWeight.split("(")
                                "${parts[0].trim()}\n(${parts[1]}"
                            } else {
                                validWeight
                            }

                        Text(
                            text = formattedWeight,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                Box(
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .size(32.sdp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { onEditClick() },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Perfil do Pet",
                        tint = Color.White,
                        modifier = Modifier.size(16.sdp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPetDetailsScreen() {
    val nav = rememberNavController()
    PetJournalTheme {
        PetDetailsScreenV2(navController = nav)
    }
}
