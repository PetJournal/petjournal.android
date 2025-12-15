package com.soujunior.petjournal.ui.screensapp.screenspets.taskListScreen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import com.soujunior.petjournal.ui.screensapp.screenspets.taskListScreen.components.TabSelector
import com.soujunior.petjournal.ui.screensapp.screenspets.taskListScreen.components.TaskDateComponent
import ir.kaaveh.sdpcompose.sdp

@Composable
fun TaskListScreen(navController: NavController) {
    //    val viewModel : TaskListViewModel = getViewModel()
    var selectedFilter by remember { mutableStateOf(DateFilter.DAILY) }

    Column(modifier = Modifier) {
        ScaffoldCustom(
            modifier =
                Modifier
                    .navigationBarsPadding()
                    .fillMaxSize(),
            navigationUp = navController,
            // TODO: remover depois que deixar de ser util para ir para a tela de cadastro de tarefas
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("schedule/registerTaskScreen")
                    },
                    containerColor = Color.Red,
                    contentColor = Color.White,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Adicionar",
                    )
                }
            },
            showBottomBarNavigation = true,
            titleTopBar = stringResource(R.string.next_tasks),
            showTopBar = true,
            bottomNavigationBar = { NavigationBar(navController) },
            contentToUse = { paddingValues ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                ) {
                    TabSelector(
                        selectedFilter = selectedFilter,
                        onFilterSelected = { newFilter ->
                            selectedFilter = newFilter
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Crossfade(targetState = selectedFilter, label = "TabTransition") { filter ->
                        when (filter) {
                            DateFilter.DAILY -> DailyTasksContent()
                            DateFilter.WEEKLY -> WeeklyTasksContent()
                            DateFilter.MONTHLY -> MonthlyTasksContent()
                        }
                    }
                }
            },
        )
    }
}

@Composable
fun DailyTasksContent() {
    LazyColumn(
        modifier = Modifier.padding(start = 16.sdp, end = 16.sdp, top = 16.sdp),
    ) {
        item {
            TaskDateComponent(
                date = "5 de Janeiro",
                tasks = TaskFakeData.sampleTasks.take(3),
                modifier = Modifier,
            )
        }
        item {
            TaskDateComponent(
                date = "3 de Fevereiro",
                tasks = TaskFakeData.sampleTasks.take(1),
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun WeeklyTasksContent() {
    LazyColumn(
        modifier = Modifier.padding(start = 16.sdp, end = 16.sdp, top = 16.sdp),
    ) {
        item {
            TaskDateComponent(
                date = "5 de Janeiro",
                tasks = TaskFakeData.sampleTasks.take(3),
                modifier = Modifier,
            )
        }
        item {
            TaskDateComponent(
                date = "30 de Setembro",
                tasks = TaskFakeData.sampleTasks.take(3),
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun MonthlyTasksContent() {
    LazyColumn(
        modifier = Modifier.padding(start = 16.sdp, end = 16.sdp, top = 16.sdp),
    ) {
        item {
            TaskDateComponent(
                date = "3 de Fevereiro",
                tasks = TaskFakeData.sampleTasks.take(1),
                modifier = Modifier,
            )
        }
        item {
            TaskDateComponent(
                date = "30 de Setembro",
                tasks = TaskFakeData.sampleTasks.take(3),
                modifier = Modifier,
            )
        }
    }
}

@Composable
@Preview
private fun ScreenPreview() {
    val nav = rememberNavController()
    TaskListScreen(nav)
}
