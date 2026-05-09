package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

import com.soujunior.petjournal.ui.util.SelectedPeriodType
import com.soujunior.petjournal.ui.util.TransactionType

sealed class RegisterTaskEvent {
    object OnCardDialogError : RegisterTaskEvent()

    object OnCardDialogAddNewTask : RegisterTaskEvent()

    object Submit : RegisterTaskEvent()

    class OnUpdateTag(val id: String, val name: String, val color: String) : RegisterTaskEvent()

    class OnCreateTag(val name: String, val color: String) : RegisterTaskEvent()

    class OnDeleteTag(val id: String) : RegisterTaskEvent()

    class OnSelectTag(val id: String?) : RegisterTaskEvent()

    class OnName(val name: String) : RegisterTaskEvent()

    class OnPetList(val ids: List<String>) : RegisterTaskEvent()

    class OnDescription(val text: String) : RegisterTaskEvent()

    class OnChangeTransactionType(val type: TransactionType) : RegisterTaskEvent()

    class OnPeriodType(val value: SelectedPeriodType) : RegisterTaskEvent()

    class OnAmPm(val value: String) : RegisterTaskEvent()

    class OnTimeChange(val value: Pair<Int, Int>) : RegisterTaskEvent()

    class OnDateChanged(val value: Long?) : RegisterTaskEvent()

    class OnDayChanged(val value: Int?) : RegisterTaskEvent()

    class OnDayOfWeekChanged(val value: String) : RegisterTaskEvent()

    class OnObservation(val value: String) : RegisterTaskEvent()

    class OnSendToApiChanged(val value: Boolean) : RegisterTaskEvent()

    object OnNextTagOnboardingStep : RegisterTaskEvent()

    object OnDismissTagOnboarding : RegisterTaskEvent()
}
