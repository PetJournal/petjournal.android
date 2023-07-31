package com.soujunior.petjournal.ui.appArea.homeScreen.components

data class CarouselModel(
    val title: String,
    val rating: Float,
    val desc: String,

)


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