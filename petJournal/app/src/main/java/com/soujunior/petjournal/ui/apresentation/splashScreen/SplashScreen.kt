package com.soujunior.petjournal.ui.apresentation.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.ImageLogo
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: SplashViewModel = getViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.popBackStack()
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {
                    delay(2000)
                    navController.popBackStack()
                    navController.navigate("accountManager")
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {

        ImageLogo(Modifier.fillMaxSize(0.6f), darkMode = true)

        Image(
            painter = painterResource(id = R.drawable.pets),
            contentDescription = "Imagem pets",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}