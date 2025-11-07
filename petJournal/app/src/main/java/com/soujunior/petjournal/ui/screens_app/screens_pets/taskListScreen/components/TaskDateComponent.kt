package com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.petjournal.ui.components.TaskCard
import com.soujunior.petjournal.ui.components.data.TaskData
import com.soujunior.petjournal.ui.components.data.TaskFakeData
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TaskDateComponent(
    date: String,
    tasks: List<TaskData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.sdp)
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.ssp,
            modifier = Modifier.padding(start = 8.sdp, bottom = 8.sdp)
        )

        tasks.forEach { taskData ->
            TaskCard(
                taskData = taskData,
                modifier = Modifier.padding(start = 8.sdp)

            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    TaskDateComponent(
        date = "5 de Janeiro",
        tasks = TaskFakeData.sampleTasks.take(3)
    )
}
