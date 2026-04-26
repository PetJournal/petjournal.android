package com.soujunior.domain.repository.task

import com.soujunior.domain.model.taskModel.ScheduleDataModel

interface TaskReminderScheduler {
    fun schedule(task: ScheduleDataModel)
    fun cancel(task: ScheduleDataModel)
}
