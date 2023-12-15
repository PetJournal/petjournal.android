package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.components.ImageLogo
import com.soujunior.petjournal.ui.components.ScaffoldCustom

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Screen(navController: NavController){

    Column(modifier = Modifier.navigationBarsPadding()){
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showTopBar = true,
            showBottomBarNavigation = false,
            contentToUse = {})
    }

}


@Preview
@Composable
private fun PreviewGenderScreen(){
    Screen(navController = rememberNavController())
}