package com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.DateFilter
import com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.components.TaskDateComponent
import com.soujunior.petjournal.ui.theme.RobotoRegular
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun Screen(navController: NavController){
//    val viewModel : TaskListViewModel = getViewModel()
    Column (modifier = Modifier){
        ScaffoldCustom(
            modifier = Modifier,
            navigationUp = navController,
            showBottomBarNavigation = true,
            titleTopBar = stringResource(R.string.next_tasks),
            showTopBar = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = { paddingValues ->
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.sdp),
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(horizontal = 14.sdp),
                        content = {
                            item{
                                TabSelector(
                                    selectedFilter = DateFilter.DAILY,
                                    onFilterSelected = {},
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                            item{
                                TaskDateComponent(
                                    date = "5 de Janeiro",
                                    tasks = TaskFakeData.sampleTasks.take(3),
                                    modifier = Modifier
                                )
                            }
                            item {
                                TaskDateComponent(
                                    date = "3 de Fevereiro",
                                    tasks = TaskFakeData.sampleTasks.take(1),
                                    modifier = Modifier
                                )
                            }
                            item{
                                TaskDateComponent(
                                    date = "30 de Setembro",
                                    tasks = TaskFakeData.sampleTasks.take(3),
                                    modifier = Modifier
                                )
                            }
                        }
                    )
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
