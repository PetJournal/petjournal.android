package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.petjournal.R
import com.soujunior.petjournal.ui.states.TaskState
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

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    private val _petName: MutableStateFlow<String> = MutableStateFlow("")
    override val petName: StateFlow<String> = _petName

    private val _isSecondItemVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isSecondItemVisible: StateFlow<Boolean> = _isSecondItemVisible

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
                // validação na viewModel para saber se foi selecionado a opção outros, mudança para validator no futuro
                _isSecondItemVisible.value = event.petRace.equals("Outro", ignoreCase = true)
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
        return state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty()
    }

    override fun change(petRace: String?, petSize: String?, petRaceOthers: String?) {
        when {
            petRace != null -> {
                state = state.copy(race = petRace)
                val result = validation.inputPetName(state.race)
                state = if (result.success) state.copy(raceError = null)
                else state.copy(raceError = result.errorMessage)

            }

            petSize != null -> {
                /*
                  Mudança para testes pois ainda não sei se tem validação para size de pet na api
                state = state.copy(size = petSize)
                val result = validation.inputPetName(state.size)
                state = if (result.success) state.copy(sizeError = null)
                else state.copy(sizeError = result.errorMessage)
                 */
                state = state.copy(size = petSize)
                state = if (state.size.isNotEmpty()) state.copy(sizeError = null)
                else state.copy(sizeError = listOf("*Campo Obrigatório."))


            }
            petRaceOthers != null -> {
                state = state.copy(raceOthers =  petRaceOthers)
                val result = validation.inputPetName(state.raceOthers)
                state = if (result.success) state.copy(raceOthersError = null)
                else state.copy(raceOthersError = result.errorMessage)
            }
        }
    }

    fun generic() {
        Log.d("Teste", state.race)
        Log.d("Teste", state.size)
        Log.d("Teste", state.raceError.toString())
        Log.d("Teste", state.sizeError.toString())
    }

    private fun getData() {
//        viewModelScope.launch {
//            _taskState.value = TaskState.Loading
//            val result =
//        }
        _sizeList.value = listOf(
            "Pequeno (até 10kg)" ,
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
            "outro"
        )
    }

}