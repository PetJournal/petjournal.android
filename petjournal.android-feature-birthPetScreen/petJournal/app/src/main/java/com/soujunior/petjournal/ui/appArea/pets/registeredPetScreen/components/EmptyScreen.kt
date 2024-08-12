package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.soujunior.petjournal.ui.components.Button3
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom

@Composable
fun EmptyScreen(navController: NavController){
    Column(modifier = Modifier.navigationBarsPadding()) {
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                Column(Modifier.fillMaxSize().padding(it)){
                    Spacer(modifier = Modifier.padding(64.dp))
                    Text(
                        text = "Lista de pets vazia!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.padding(64.dp))
                    Button3(
                        submit = { navController.navigate("pets/introRegisterPet") },
                        enableButton = true,
                        text = "Cadastrar Pet")
                }
            }
        )

    }
}

@Preview
@Composable
fun PreviewEmptyScreen(){
    EmptyScreen(navController = NavController(context = LocalContext.current))
}