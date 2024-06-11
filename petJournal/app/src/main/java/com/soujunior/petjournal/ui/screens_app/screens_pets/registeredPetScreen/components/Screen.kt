package com.soujunior.petjournal.ui.screens_app.screens_pets.registeredPetScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.PetItemCard
import com.soujunior.petjournal.ui.components.ScaffoldCustom

@Composable
fun Screen(navController: NavController,
           registeredPetsList: List<PetInformationModel>){


    ScaffoldCustom( modifier = Modifier.navigationBarsPadding(),
        navigationUp = navController,
        showTopBar = true,
        showBottomBarNavigation = true,
        floatingActionButton = {
           FloatingActionButton(onClick = {navController.navigate("pets/introRegisterPet")}) {
               Icon(Icons.Default.Add, contentDescription = "Adicionar Pet")
           }
        },
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