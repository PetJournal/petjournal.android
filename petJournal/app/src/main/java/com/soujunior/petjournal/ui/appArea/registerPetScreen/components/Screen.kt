package com.soujunior.petjournal.ui.appArea.registerPetScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom

@Composable
fun Screen(navController: NavController) {
    ScaffoldCustom(
        modifier = Modifier.statusBarsPadding().navigationBarsPadding(),
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
                            navController.navigate("pets/speciesChoice")
                        }, enableButton = true, text = "Continuar")
                    }
                })
        })
}