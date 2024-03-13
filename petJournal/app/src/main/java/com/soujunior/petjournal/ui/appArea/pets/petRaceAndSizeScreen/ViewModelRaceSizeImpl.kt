package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelRaceSizeImpl(
    val validation: ValidationRepository
) : ViewModelRaceSize() {

    override var state by mutableStateOf(RaceSizeFormState())
    override val validationEventChannel: Channel<ValidationEvent>
        get() = TODO("Not yet implemented")
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")

    private val _isTextFiledOthersVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isTextFiledOthersVisible: StateFlow<Boolean> = _isTextFiledOthersVisible

    private val _sizeList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    override val sizeList: StateFlow<List<String>> = _sizeList

    private val _raceList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    override val raceList: StateFlow<List<String>> = _raceList


    init {
        getData()

    }

    /*Os metodos de Success e Failure vão ser utilizados
    quando houver a nova impl do dataclass*/
    override fun success() {
        TODO("teste")
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun onEvent(event: RaceSizeFormEvent) {
        when (event) {
            is RaceSizeFormEvent.PetRace -> {
                change(petRace = event.petRace)
            }

            is RaceSizeFormEvent.PetSize -> change(petSize = event.petSize)
            is RaceSizeFormEvent.PetRaceOthers -> change(petRaceOthers = event.petRaceOthers)
            is RaceSizeFormEvent.NextButton -> {
                change(petRace = state.race)
                change(petSize = state.size)
                change(petRaceOthers = state.raceOthers)
            }

            else -> {}
        }
    }

    override fun enableButton(): Boolean {

        return if (_isTextFiledOthersVisible.value) {
            state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty() && state.raceOthersError.isNullOrEmpty()
        } else {
            state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty()
        }

    }

    override fun enableRaceOthers(): Boolean {
        val raceOtherResult = validation.validateDropDownRaceOthers(state.race)
        _isTextFiledOthersVisible.value = raceOtherResult.success
        return raceOtherResult.success
    }

    override fun change(petRace: String?, petSize: String?, petRaceOthers: String?) {
        when {
            petSize != null -> {
                state = state.copy(size = petSize)
                val result = validation.validateDropdownSize(state.size, sizeList.value)
                state = if (result.success) state.copy(sizeError = null)
                else state.copy(sizeError = result.errorMessage)

            }

            petRace != null -> {
                state = state.copy(race = petRace)
                val result = validation.validatePetRaceList(state.race, raceList.value)
                state = if (result.success) state.copy(raceError = null)
                else state.copy(raceError = result.errorMessage)

            }

            petRaceOthers != null -> {
                if (_isTextFiledOthersVisible.value) {
                    state = state.copy(raceOthers = petRaceOthers)
                    val result = validation.inputPetName(state.raceOthers)
                    state = if (result.success) state.copy(raceOthersError = null)
                    else state.copy(raceOthersError = result.errorMessage)
                }
            }
        }
    }


    private fun getData() {
        _sizeList.value = listOf(
            "Pequeno (até 10kg)",
            "Médio (11 à 24kg)",
            "Grande (a cima de 25kg)"
        )
        _raceList.value = listOf(
            "Afghan Hound",
            "Affenpinscher",
            "Airedale Terrier",
            "Akita",
            "American Staffordshire Terrier",
            "Basenji",
            "Basset Hound",
            "Beagle",
            "Beagle Harrier",
            "Bearded Collie",
            "Bedlington Terrier",
            "Bichon Frisé",
            "Bloodhound",
            "Bobtail",
            "Boiadeiro Australiano",
            "Boiadeiro Bernês",
            "Border Collie",
            "Border Terrier",
            "Borzoi",
            "Boston Terrier",
            "Boxer",
            "Outro"
        )
    }

}