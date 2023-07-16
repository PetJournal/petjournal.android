package com.soujunior.petjournal.ui.components

import androidx.annotation.DrawableRes
import com.soujunior.petjournal.R

sealed class NavigationBarItems(var title : String, var route : String, @DrawableRes var icons: Int){
    object Home : NavigationBarItems(
        title = "home",
        route = "home",
        icons = R.drawable.home_button_bar
    )
       object Pets : NavigationBarItems(
        title = "pets",
        route = "login",//TODO:Alterar para a rota da tela de pets
        icons = R.drawable.pets_button_bar
    )
       object Tutor : NavigationBarItems(
        title = "tutor",
        route = "register",//TODO:Alterar para a rota da tela de tutor
        icons = R.drawable.tutor_button_bar
    )
}