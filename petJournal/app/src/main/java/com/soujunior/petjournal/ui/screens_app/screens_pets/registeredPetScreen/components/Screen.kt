package com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.ViewModelRegisteredPets
import com.soujunior.petjournal.ui.components.DeleteDialog
import com.soujunior.petjournal.ui.components.IndeterminateCircularIndicator
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetItemCard
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.states.TaskState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun Screen(navController: NavController) {
    val viewModel: ViewModelRegisteredPets = getViewModel()
    val taskState by viewModel.taskState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var actualPetId: Long by remember { mutableStateOf(0) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = taskState is TaskState.Loading,
        onRefresh = {
            viewModel.deleteAllPetInformation()
            viewModel.getAllPetInformation()
        }
    )

    ScaffoldCustom(modifier = Modifier.navigationBarsPadding(),
        navigationUp = navController,
        showTopBar = true,
        showBottomBarNavigation = true,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag("AddNewPet"),
                onClick = { navController.navigate("pets/introRegisterPet") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Pet")
            }
        },
        bottomNavigationBar = { NavigationBar(navController) },
        titleTopBar = "Lista de Pets Cadastrados",
        contentToUse = {
            Box(modifier = Modifier.padding()) {
                if (taskState is TaskState.Loading)
                    IndeterminateCircularIndicator(modifier = Modifier.align(Alignment.Center))
                else {
                    if (viewModel.state.registeredPetList.isEmpty())
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(it)
                        ) {
                            (
                                    Text(
                                        text = "Cadastre as informações do seus pets clicando no botão abaixo",
                                        modifier = Modifier.padding(it),
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    )
                                    )
                        }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(it)
                            .pullRefresh(pullRefreshState)
                            .testTag("ListOfPets")
                    ) {
                        items(items = viewModel.state.registeredPetList,
                            itemContent = { item ->
                                PetItemCard(
                                    item,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .combinedClickable
                                            (onClick = {
                                            /*Abrir Tela do Pet*/
                                        },
                                            onLongClick = {
                                                actualPetId = item.idLocal!!
                                                showDeleteDialog = true
                                            })
                                )
                            })
                    }

                    PullRefreshIndicator(
                        refreshing = taskState is TaskState.Loading,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )

                }
            }
        })

    if (showDeleteDialog) {
        DeleteDialog(
            onDismissRequest = { showDeleteDialog = false },
            // TODO: ADICIONAR ID DA API PARA RESOLVER ESSA REMOÇÃO DE DADOS
            onConfirmation = { viewModel.deletePetInformation(actualPetId) },
            dialogTitle = "Deletar Card",
            dialogText = "Deseja mesmo deletar as informações deste pet?"
        )
    }
}

//@Composable
//@Preview
//private fun PreviewScreen(){
//    val registeredPetsList = listOf(
//        PetInformationModel(0, name = "Jake Tesouro", petRace = "Pointer", petAge = "10", gender = "F"),
//        PetInformationModel(1, name = "Jake Tesouro", petRace = "Pointer", petAge = "10", gender = "M"),
//        PetInformationModel(2, name = "Jake Tesouro", petRace = "Pointer", petAge = "10", gender = "F")
//    )
//    Screen(navController = NavController(context = LocalContext.current),
//        registeredPetsList = registeredPetsList)
//}

