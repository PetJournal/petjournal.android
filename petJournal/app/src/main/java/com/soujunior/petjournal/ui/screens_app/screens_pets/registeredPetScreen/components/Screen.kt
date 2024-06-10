package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen.ViewModelRegisteredPets
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetItemCard
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.states.TaskState
import org.koin.androidx.compose.getViewModel

@Composable
fun Screen(navController: NavController,
           registeredPetsList: List<PetInformationModel>,
           taskState: TaskState){


    ScaffoldCustom( modifier = Modifier.navigationBarsPadding(),
        navigationUp = navController,
        showTopBar = true,
        showBottomBarNavigation = true,
        bottomNavigationBar = { NavigationBar(navController) },
        titleTopBar = "Lista de Pets Cadastrados",
        contentToUse = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)){
                items(items = registeredPetsList,
                    itemContent = {
                    item -> PetItemCard(item)
                })
            }
        })
}

@Composable
@Preview
private fun PreviewScreen(){
//    Screen(navController = NavController(context = LocalContext.current))
}