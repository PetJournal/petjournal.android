package com.soujunior.petjournal.ui.apresentation.splashScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.accountManager.loginScreen.LoginViewModel
import com.soujunior.petjournal.ui.components.ImageLogo
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
                    navController.navigate("mainContent")
                }

                is ValidationEvent.Failed -> {  }
            }
        }
    }
    //TODO: Encontrar abordagem que não troque as cores do NavigationBar e do StatusBar antes de trocar de tela
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colorScheme.background)
    systemUiController.setNavigationBarColor(Color.White)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        ImageLogo(Modifier.fillMaxSize(0.6f), darkMode = true)

        Image(
            painter = painterResource(id = R.drawable.pets),
            contentDescription = "Imagem pets",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("accountManager")
    }
}