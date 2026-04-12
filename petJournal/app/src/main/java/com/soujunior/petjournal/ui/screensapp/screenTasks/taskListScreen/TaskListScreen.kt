package com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.components.NavigationBar
import com.soujunior.petjournal.ui.components.ScaffoldCustom
import com.soujunior.petjournal.ui.components.TaskListItemShimmer
import com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen.components.TabSelector
import com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen.components.TaskDateComponent
import com.soujunior.petjournal.ui.util.toDailyGroupFormat
import com.soujunior.petjournal.ui.util.toMonthlyGroupFormat
import com.soujunior.petjournal.ui.util.toWeeklyGroupFormat
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.getViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
private fun getCorrectViewModel(): TaskListViewModel {
    return if (LocalInspectionMode.current) {
        FakeTaskListViewModel()
    } else {
        getViewModel()
    }
}

@Composable
fun TaskListScreen(navController: NavController) {
    val viewModel: TaskListViewModel = getCorrectViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier) {
        ScaffoldCustom(
            modifier = Modifier.fillMaxSize().navigationBarsPadding().statusBarsPadding(),
            navigationUp = navController,
            floatingActionButton = {
                Surface(
                    modifier =
                        Modifier
                            .size(32.sdp)
                            .clickable(onClick = {
                                navController.navigate("schedule/registerTaskScreen")
                            }),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.addpet),
                            tint = Color.White,
                            modifier = Modifier.size(32.sdp),
                        )
                    }
                }
            },
            showBottomBarNavigation = true,
            titleTopBar = stringResource(R.string.next_tasks),
            showTopBar = true,
            bottomNavigationBar = {
                NavigationBar(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding(),
                )
            },
            contentToUse = { paddingValues ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                ) {
                    TabSelector(
                        selectedFilter = state.selectedDateFilter,
                        onFilterSelected = { newFilter ->
                            viewModel.onEvent(TaskListEvent.OnDateFilterChange(newFilter))
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    if (state.isLoading) {
                        LazyColumn(
                            modifier = Modifier.padding(start = 16.sdp, end = 16.sdp, top = 16.sdp),
                        ) {
                            items(3) {
                                TaskListItemShimmer()
                            }
                        }
                    } else {
                        val groupedTasks =
                            state.tasks.groupBy { task ->
                                when (state.selectedDateFilter) {
                                    DateFilter.DAILY -> task.startAt.toDailyGroupFormat()
                                    DateFilter.WEEKLY -> task.startAt.toWeeklyGroupFormat()
                                    DateFilter.MONTHLY -> task.startAt.toMonthlyGroupFormat()
                                }
                            }
                        LazyColumn(
                            modifier = Modifier.padding(start = 16.sdp, end = 16.sdp, top = 16.sdp),
                        ) {
                            groupedTasks.forEach { (dateStr, groupTasks) ->
                                item {
                                    TaskDateComponent(
                                        date = dateStr,
                                        tasks = groupTasks,
                                        modifier = Modifier.padding(bottom = 16.sdp),
                                    )
                                }
                            }
                        }
                    }
                }
            },
        )
    }
}

@Composable
@Preview
private fun ScreenPreview() {
    val nav = rememberNavController()
    TaskListScreen(nav)
}
