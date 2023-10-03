package com.soujunior.petjournal.ui.components

import androidx.annotation.DrawableRes
import com.soujunior.petjournal.R

sealed class NavigationBarItems(
    var title: String,
    var route: String,
    @DrawableRes var icons: Int,
    var group: String
) {
    object Home : NavigationBarItems(
        title = "home",
        route = "home",
        icons = R.drawable.home_button_bar,
        group = "home"
    )
    object Pets : NavigationBarItems(
        title = "pets",
        route = "pets/registerPet",
        icons = R.drawable.pets_button_bar,
        group = "pets"
    )
    object Tutor : NavigationBarItems(
        title = "tutor",
        route = "tutorScreen",
        icons = R.drawable.tutor_button_bar,
        group = "tutor"
    )
}
