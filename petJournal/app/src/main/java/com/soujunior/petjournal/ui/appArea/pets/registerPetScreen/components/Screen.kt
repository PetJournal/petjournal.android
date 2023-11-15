package com.soujunior.petjournal.ui.appArea.pets.registerPetScreen.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.appArea.pets.registerPetScreen.RegisterPetViewModel
import com.soujunior.petjournal.ui.components.Button2
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.util.ValidationEvent
import org.koin.androidx.compose.getViewModel

@Composable
fun Screen(navController: NavController) {
    Log.e(TAG, "Entrou na register pet")
    val viewModel: RegisterPetViewModel = getViewModel()
    val context = LocalContext.current
    var state by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    state = viewModel.visualizedScreen.value
                }

                is ValidationEvent.Failed -> {
                    Log.e(TAG, "Erro")
                }
            }
        }
    }
    if (!state){
        Column(modifier = Modifier.navigationBarsPadding()){
            ScaffoldCustom(
                modifier = Modifier,
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
                                Button2(
                                    submit = {
                                        viewModel.setWasViewed()
                                    },
                                    enableButton = true,
                                    text = "Continuar"
                                )
                            }
                        })
                })
        }
    }else{
        var name = "Fulano"
        navController.navigate("pets/speciesChoice/$name")
    }


}