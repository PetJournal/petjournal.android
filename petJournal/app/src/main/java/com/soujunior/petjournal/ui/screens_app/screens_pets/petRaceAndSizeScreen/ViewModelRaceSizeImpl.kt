package com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.pet.GetListPetSizesUseCase
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelRaceSizeImpl(
    val validation: ValidationRepository,
    private val getPetInformationUseCase: GetPetInformationUseCase,
    private val updatePetInformationUseCase: UpdatePetInformationUseCase,
    private val getListPetSizesUseCase: GetListPetSizesUseCase
) : ViewModelRaceSize() {

    override var state by mutableStateOf(RaceSizeFormState())
    override val validationEventChannel get() = Channel<ValidationEvent>()
    override val message: StateFlow<String> get() = _message
    private val _message = MutableStateFlow("")

    private val _isTextFiledOthersVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isTextFiledOthersVisible: StateFlow<Boolean> get() = _isTextFiledOthersVisible

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState
    override val shouldScrollToTop = MutableStateFlow(false)

    override fun success(petInformationModel: PetInformationModel) {
        state = state.copy(specie = petInformationModel.species ?: "")
        state = state.copy(idPetInformation = petInformationModel.id)
        state = state.copy(name = petInformationModel.name ?: "")
        state = state.copy(gender = petInformationModel.gender ?: "")


        getListRacePets()
        requestGetListSizes()

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _message.value = "Sucesso"
    }

    override fun successGetPetSizes(listPetSizes: List<PetSizeItemModel>) {
        state = state.copy(listSizes = listPetSizes)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _message.value = "Sucesso"
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
        _message.value = "Error"
    }

    override fun onEvent(event: RaceSizeFormEvent) {
        when (event) {
            is RaceSizeFormEvent.ScrollToTop -> {
                shouldScrollToTop.value = event.scrollToTop
            }

            is RaceSizeFormEvent.PetRace -> {
                change(petRace = event.petRace)
            }

            is RaceSizeFormEvent.PetSize -> change(petSize = event.petSize)
            is RaceSizeFormEvent.PetRaceOthers -> change(petRaceOthers = event.petRaceOthers)
            is RaceSizeFormEvent.NextButton -> {
                change(petRace = state.race)
                change(petSize = state.size)
                if (_isTextFiledOthersVisible.value) {
                    change(petRaceOthers = state.raceOthers)
                }
            }

            else -> {}
        }
    }

    override fun enableButton(): Boolean {

        return if (enableRace()) {
            if (_isTextFiledOthersVisible.value) {
                state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty() && state.raceOthersError.isNullOrEmpty()
            } else {
                state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty()
            }
        } else {
            state.sizeError.isNullOrEmpty()
        }

    }

    override fun enableRaceOthers(): Boolean {
        val raceOtherResult = validation.validateDropDownRaceOthers(state.race)
        _isTextFiledOthersVisible.value = raceOtherResult.success
        return raceOtherResult.success
    }

    override fun enableRace(): Boolean {
        return state.specie == "Cat" || state.specie == "Dog"
    }

    override fun change(petRace: String?, petSize: String?, petRaceOthers: String?) {
        when {
            petSize != null -> {
                state = state.copy(size = petSize)
                val result = validation.validateDropdown(state.size, state.listSizes)
                state = if (result.success) state.copy(sizeError = null)
                else state.copy(sizeError = result.errorMessage)

            }

            petRace != null -> {
                state = state.copy(race = petRace)
                val result = validation.validateDropDownPetRace(state.race, state.listRace)
                state = if (result.success) state.copy(raceError = null)
                else state.copy(raceError = result.errorMessage)

            }

            petRaceOthers != null -> {
                state = state.copy(raceOthers = petRaceOthers)
                val result = validation.inputPetName(state.raceOthers)
                state = if (result.success) state.copy(raceOthersError = null)
                else state.copy(raceOthersError = result.errorMessage)

            }
        }
    }

    override fun getPetInformation(id: Long) {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = getPetInformationUseCase.execute(id)
            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle
        }
    }

    override fun updatePetInformation() {

        val petRace = if (state.race.lowercase() == "outro") state.raceOthers else state.race

        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val petInformation = PetInformationModel(
                id = state.idPetInformation ?: 0L,
                species = state.specie,
                name = state.name,
                gender = state.gender,
                size = state.size,
                petRace = petRace,
                guardianId = 1
            )

            val result = updatePetInformationUseCase.execute(petInformation)

            result.handleResult(::successPetUpdate, ::failed)
            _taskState.value = TaskState.Idle
        }
    }

    override fun successPetUpdate(unit: Unit) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _message.value = "Sucesso"
    }

    override fun getListRacePets() {
        if (state.specie == "Cat") {
            requestListFakeCats()
        } else if (state.specie == "Dog") {
            requestListFakeDogs()
        }
    }


    private fun requestListFakeCats() {
        state = state.copy(
            listRace = listOf(
                "Abissínio",
                "American Bobtail de Pelo Curto",
                "American Bobtail de Pelo Longo",
                "American Shorthair",
                "American Wirehair",
                "Angorá Turco",
                "Arabian Mau",
                "Ashera",
                "Australian Mist",
                "Balinês",
                "Bengal",
                "Bobtail americano",
                "Bobtail japonês",
                "Bombay",
                "Brazilian Shorthair",
                "British de Pelo Longo",
                "Burmês",
                "Burmês vermelho",
                "California Spangled",
                "Chartreux",
                "Chausie",
                "Cornish Rex",
                "Curl Americano de Pelo Curto",
                "Curl Americano de Pelo Longo",
                "Cymric",
                "Devon Rex",
                "Doméstico de Pelo Curto",
                "Doméstico de Pelo Longo",
                "Don Sphynx",
                "Egyptian Mau",
                "Europeu",
                "Exótico de Pelo Curto",
                "Gato Asiático de Pelo Semi-Longo",
                "German Rex",
                "Havana",
                "Himalaio",
                "Khao Manee",
                "Korat",
                "Kurilian Bobtail de Pelo Curto",
                "Kurilian Bobtail de Pelo Longo",
                "LaPerm de Pelo Curto",
                "LaPerm de Pelo Longo",
                "Maine Coon",
                "Manx",
                "Mekong Bobtail",
                "Mistura de serval, gato-leopardo e gato doméstico (Ashera)",
                "Munchkin de Pelo Curto",
                "Munchkin de Pelo Longo",
                "Nebelung",
                "Ocicat",
                "Ojos Azules de Pelo Curto",
                "Oriental de Pelo Curto",
                "Oriental de Pelo Longo",
                "Persa",
                "Peterbald",
                "Pixiebob de Pelo Curto",
                "Pixiebob de Pelo Longo",
                "Ragamuffin",
                "Ragdoll",
                "Russo Azul",
                "Sagrado da Birmânia",
                "Savannah",
                "Scottish Fold",
                "Selkirk Rex de Pelo Curto",
                "Selkirk Rex de Pelo Longo",
                "Serengeti",
                "Sem Raça Definida (SRD)",
                "Siamês",
                "Siberiano",
                "Singapura",
                "Snowshoe",
                "Sokoke",
                "Somali",
                "Sphynx",
                "Thai",
                "Tonquinês de Pelo Curto",
                "Toyger",
                "Van Turco",
                "York Chocolate",
                "Outro"
            )
        )
    }

    private fun requestListFakeDogs() {
        state = state.copy(
            listRace = listOf(
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
                "Buldogue Francês",
                "Buldogue Inglês",
                "Bull Terrier",
                "Bulmastife",
                "Cairn Terrier",
                "Cane Corso",
                "Cão de Água Português",
                "Cão de Crista Chinês",
                "Cavalier King Charles Spaniel",
                "Chesapeake Bay Retriever",
                "Chihuahua",
                "Chow Chow",
                "Cocker Spaniel Americano",
                "Cocker Spaniel Inglês",
                "Collie",
                "Coton de Tuléar",
                "Dachshund",
                "Dálmata",
                "Dandie Dinmont Terrier",
                "Dobermann",
                "Dogo Argentino",
                "Dogue Alemão",
                "Fila Brasileiro",
                "Fox Terrier (Pelo Duro e Pelo Liso)",
                "Foxhound Inglês",
                "Galgo Escocês",
                "Galgo Irlandês",
                "Golden Retriever",
                "Grande Boiadeiro Suiço",
                "Greyhound",
                "Grifo da Bélgica",
                "Husky Siberiano",
                "Jack Russell Terrier",
                "King Charles",
                "Komondor",
                "Labradoodle",
                "Labrador Retriever",
                "Leonberger",
                "Lhasa Apso",
                "Malamute do Alasca",
                "Maltês",
                "Mastife",
                "Mastim Napolitano",
                "Norfolk Terrier",
                "Norwich Terrier",
                "Papillon",
                "Pastor Alemão",
                "Pinscher Miniatura",
                "Poodle",
                "Pug",
                "Rottweiler",
                "Sem Raça Definida (SRD)",
                "ShihTzu",
                "Silky Terrier",
                "Skye Terrier",
                "Staffordshire Bull Terrier",
                "Terrier Escocês",
                "Yorkshire Terrier",
                "Outro"
            )
        )
    }

    override fun requestGetListSizes() {
        _taskState.value = TaskState.Loading
        if (state.specie == "Cat" || state.specie == "Dog") {
            viewModelScope.launch {
                val result = getListPetSizesUseCase.execute(state.specie)
                result.handleResult(::successGetPetSizes, ::failed)
                _taskState.value = TaskState.Idle
            }
        }

    }

}