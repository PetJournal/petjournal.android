package com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen

import androidx.lifecycle.ViewModel
import com.petjournal.database.database.entity.ListPetSizes
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

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
    abstract fun getListRacePets()
    abstract fun requestGetListSizes()
}