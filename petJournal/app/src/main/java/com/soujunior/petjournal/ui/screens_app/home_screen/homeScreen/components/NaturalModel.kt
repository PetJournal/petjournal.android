package com.soujunior.petjournal.ui.screens_app.home_screen.homeScreen.components

data class CarouselModel(
    val title: String,
    val rating: Float,
    val desc: String,

)
//Todo: (Gelson)? esse também não entendi
val natural = listOf(
    CarouselModel(
        "Sample 1",
        4.0f,
        "Decription Sample 1",
    ),
    CarouselModel(
        "Sample 2",
        4.5f,
        "Decription Sample 2",
    ),
    CarouselModel(
        "Sample 3",
        5.0f,
        "Decription Sample 3",
    )
)