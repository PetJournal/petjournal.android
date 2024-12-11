package com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeRaceAndSizeViewModel : ViewModelRaceSize() {
    override var state: RaceSizeFormState
        get() = RaceSizeFormState()
        set(value) {}
    override val validationEventChannel = Channel<ValidationEvent>()
    override val message = MutableStateFlow("Mensagem de Teste")
    override val validationEvents = emptyFlow<ValidationEvent>()
    override fun success(petInformationModel: PetInformationModel) {}

    override fun successGetPetSizes(listPetSizes: List<PetSizeItemModel>) {}

    override fun successGetPetRaces(listPetRaces: List<PetRaceItemModel>) {}

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
    override val isTextFiledOthersVisible: StateFlow<Boolean> = MutableStateFlow(true)

    override fun failed(exception: Throwable?) { /* No-op */
    }

    override fun onEvent(event: RaceSizeFormEvent) {}

    override fun enableButton(): Boolean = true
    override fun enableRaceOthers(): Boolean = false

    override fun enableRace(): Boolean = true

    override val shouldScrollToTop: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun change(petRace: String?, petSize: String?, petRaceOthers: String?) {}

    override fun getPetInformation(id: Long) {}
    override fun updatePetInformation() {}

    override fun successPetUpdate(unit: Unit) {}
    override suspend fun requestGetListSizes() {}

    override suspend fun requestGetListRaces() {}

}

abstract class ViewModelRaceSize : ViewModel() {
    abstract var state: RaceSizeFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    abstract val isTextFiledOthersVisible: StateFlow<Boolean>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(petInformationModel: PetInformationModel)
    abstract fun successGetPetSizes(listPetSizes: List<PetSizeItemModel>)
    abstract fun successGetPetRaces(listPetRaces: List<PetRaceItemModel>)
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: RaceSizeFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun enableRaceOthers(): Boolean
    abstract fun enableRace(): Boolean
    abstract val shouldScrollToTop: MutableStateFlow<Boolean>
    abstract fun change(
        petRace: String? = null,
        petSize: String? = null,
        petRaceOthers: String? = null,
    )

    abstract fun getPetInformation(id: Long)
    abstract fun updatePetInformation()

    abstract fun successPetUpdate(unit: Unit)
    abstract suspend fun requestGetListSizes()
    abstract suspend fun requestGetListRaces()
}