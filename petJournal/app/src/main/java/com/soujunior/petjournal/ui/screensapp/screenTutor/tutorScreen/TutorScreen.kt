package com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.components.ActionItem
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TrailBack
import com.soujunior.petjournal.ui.components.UserProfileHeader
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TutorScreen(navController: NavController) {
    ScaffoldCustom(
        modifier =
            Modifier
                .navigationBarsPadding()
                .fillMaxSize(),
        navigationUp = navController,
        bottomNavigationBar = { NavigationBar(navController) },
        showBottomBarNavigation = true,
        contentToUse = {
            TrailBack()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = it,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    UserProfileHeader(
                        name = "Carla Westervelt",
                        email = "yourname@gmail.com",
                        imageUrl = null,
                    )
                    HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
                    ActionItem(
                        title = "Alterar senha",
                        onClick = {},
                    )
                    ActionItem(
                        title = "Notificações",
                        onClick = { navController.navigate("profile/notificationScreen") },
                    )
                    ActionItem(
                        title = "Política de Privacidade",
                        onClick = { navController.navigate("profile/privacyPolicyScreen") },
                    )
                    ActionItem(
                        title = "Quem somos",
                        onClick = {},
                    )
                    ActionItem(
                        title = "Excluir conta",
                        onClick = {},
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TutorScreenPreview() {
    PetJournalTheme {
        TutorScreen(navController = rememberNavController())
    }
}
