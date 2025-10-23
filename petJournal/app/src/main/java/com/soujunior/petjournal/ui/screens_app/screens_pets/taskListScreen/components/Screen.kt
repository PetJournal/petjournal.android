package com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.screens_app.screens_pets.registerPetScreen.components.TabSelector
import com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.DateFilter
import com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.TaskListViewModel
import com.soujunior.petjournal.ui.theme.RobotoRegular
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.getViewModel

@Composable
fun Screen(navController: NavController){
//    val viewModel : TaskListViewModel = getViewModel()
    Column (modifier = Modifier){
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showBottomBarNavigation = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = {
                Column(modifier = Modifier.padding(16.sdp)){

                    Text(text = stringResource(R.string.next_tasks),
                        fontSize = 16.ssp,
                        lineHeight = 24.ssp,
                        fontFamily = FontFamily(RobotoRegular),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF222222),
                        textAlign = TextAlign.Center)

                    TabSelector(
                        selectedFilter = DateFilter.DAILY,
                        onFilterSelected = {},
                        modifier = Modifier.fillMaxWidth().padding(top = 16.sdp)
                    )

                }
            }
        )

    }
}

@Composable
@Preview
private fun ScreenPreview(){
    val nav = rememberNavController()
    Screen(nav)
}