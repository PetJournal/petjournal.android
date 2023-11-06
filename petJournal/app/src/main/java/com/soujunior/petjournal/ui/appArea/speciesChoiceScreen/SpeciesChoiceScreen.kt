package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.appArea.speciesChoiceScreen.components.Screen
import org.koin.androidx.compose.getViewModel


@Composable
fun SpeciesChoiceScreen(navController: NavController) {
    val viewModel: ViewModelChoiceSpecies = getViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    //navController.navigate("login")
                }

                is ValidationEvent.Failed -> {
                    //Toast.makeText(context, viewModel.message.value, Toast.LENGTH_LONG).show()
                    //Log.e(ContentValues.TAG, "Layout register: " + viewModel.message.value)
                }
            }
        }
    }
    Screen(navController, viewModel)
}

@Preview
@Composable
fun PreviewHeader() {
    val navController = rememberNavController()
    SpeciesChoiceScreen(navController)
}
