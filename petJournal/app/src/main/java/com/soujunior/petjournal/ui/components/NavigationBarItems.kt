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
        title = "Home",
        route = "home",
        icons = R.drawable.ic_home,
        group = "home"
    )

    object Schedule : NavigationBarItems(
        title = "Agenda",
        route = "scheduleScreen",
        icons = R.drawable.ic_schedule,
        group = "schedule"
    )

    object Pets : NavigationBarItems(
        title = "Pets",
        route = "pets/petListScreen",
        icons = R.drawable.ic_pet,
        group = "pets"
    )

    object Profile : NavigationBarItems(
        title = "Perfil",
        route = "profileScreen",
        icons = R.drawable.ic_profile,
        group = "profile"
    )
}
