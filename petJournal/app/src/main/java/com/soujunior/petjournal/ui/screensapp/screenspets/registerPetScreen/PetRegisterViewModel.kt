package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.response.pet.BreedModel
import com.soujunior.domain.model.response.pet.SizeModel
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class StateUI(
    val pet: PetModel? = null,
    val idPetSelected: String? = null,
    val showDialogSuccess: Boolean = false,
    val showDialogError: Boolean = false,
    val isLoadingBreeds: Boolean = false,
    val isLoadingSizes: Boolean = false,
    val messageError: String? = null,
    val petImage: String? = null,
    val petName: String? = null,
    val selectedAnimalType: String? = null,
    val petRace: String? = null,
    val petSize: String? = null,
    val petBirthday: String? = null,
    val petGender: String? = null,
    val petCastrated: Boolean? = null,
    val listRaceOnly: List<String> = emptyList(),
    val listRace: List<BreedModel> = emptyList(),
    val listSizeOnly: List<String> = emptyList(),
    val listSize: List<SizeModel> = emptyList(),
    val listAnimalTypes: List<String> = listOf("cachorro", "gato"),
) {
    // todo: abstrair os nomes dos animais para um enum ou constante
    fun convert(input: String): String {
        val animalMap =
            mapOf(
                "gato" to "cat",
                "cachorro" to "dog",
            )

        return animalMap[input.lowercase()] ?: ""
    }

    fun buildPetModel(): PetModel {
        return PetModel(
            petName = this.petName,
            species = this.selectedAnimalType,
            gender = this.petGender,
            petRace = this.petRace,
            size = this.petSize,
            weight = null,
            dateOfBirth = this.petBirthday,
            castrated = this.petCastrated,
            image = this.petImage,
        )
    }
}

@JvmName("sizeModelListToString")
fun List<SizeModel>.toList(): List<String> {
    return this.map { it.name.toString() }
}

@JvmName("breedModellListToString")
fun List<BreedModel>.toList(): List<String> {
    return this.map { it.name.toString() }
}

sealed class CreatePetEvent {
    data class OnInputImage(val image: String) : CreatePetEvent()

    data class OnInputName(val name: String) : CreatePetEvent()

    data class OnTypeSelected(val type: String) : CreatePetEvent()

    data class OnInputRace(val breed: String) : CreatePetEvent()

    data class OnInputSize(val size: String) : CreatePetEvent()

    data class OnInputBirthday(val birthday: String) : CreatePetEvent()

    data class OnInputSex(val sex: String) : CreatePetEvent()

    data class OnInputCastrated(val isCastrated: Boolean) : CreatePetEvent()

    object OnSubmit : CreatePetEvent()
}

class FakePetRegisterViewModel() : PetRegisterViewModel() {
    override val stateUi: StateFlow<StateUI> get() {
        TODO()
    }
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun onEvent(event: CreatePetEvent) {}
}

abstract class PetRegisterViewModel : ViewModel() {
    abstract val stateUi: StateFlow<StateUI>
    abstract val taskState: StateFlow<TaskState>

    abstract fun onEvent(event: CreatePetEvent)
}
