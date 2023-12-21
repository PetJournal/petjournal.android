package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

import androidx.lifecycle.ViewModel

abstract class ViewModelNameGender : ViewModel(){
    abstract var state: NameGenderState

    abstract fun onEvent(event: NameGenderFormEvent)
}