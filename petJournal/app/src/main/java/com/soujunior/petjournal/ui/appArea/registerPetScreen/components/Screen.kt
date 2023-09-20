package com.soujunior.petjournal.ui.appArea.registerPetScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.util.UserViewModel

@Composable
fun Screen(navController: NavController, userViewModel: UserViewModel) {
    /*(Simão) O top bar não estava sendo exibido devido a você não ter definido.
    Só chamar o Scaffold não faz o titulo aparecer, ao contrário do que o preview deixava entender.
    Criei esse componente ScaffoldCustom pra tentar manter um padrão no time, tente usa-lo, e em
    caso de necessidade, aprimora-lo*/
    ScaffoldCustom(
        titleTopBar = "Cadastro Pet",
        showButtonToReturn = true,
        navigationUp = navController,
        showTopBar = true,
        showBottomBarNavigation = true,
        bottomNavigationBar = { NavigationBar(navController) },
        contentToUse = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background),
                content = {
                    item { Header() }
                    item { GridVectors() }
                    item {
                        Button2(submit = {
                            navController.navigate("speciesChoice")
                        }, enableButton = true, text = "Continuar")
                    }
                })
        })
}