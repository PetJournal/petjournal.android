package com.soujunior.petjournal.ui.screensapp.screenTutor.privacyPolicy

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.theme.PetJournalTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    ScaffoldCustom(
        modifier =
            Modifier
                .navigationBarsPadding()
                .fillMaxSize(),
        navigationUp = navController,
        showTopBar = true,
        titleTopBar = stringResource(R.string.notifications_title),
        bottomNavigationBar = { NavigationBar(navController) },
        showButtonToReturn = true,
        showBottomBarNavigation = true,
        contentToUse = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = it,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    PrivacyPolicyWVScreen("https://petjournalterms.blogspot.com/2024/03/privacy-policy-this-privacy-policy.html")
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TutorScreenPreview() {
    PetJournalTheme {
        PrivacyPolicyScreen(navController = rememberNavController())
    }
}
