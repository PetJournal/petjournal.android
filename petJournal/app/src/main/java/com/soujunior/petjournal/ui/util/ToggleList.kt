package com.soujunior.petjournal.ui.util

fun List<String>.toggle(item: String): List<String> {
    return if (contains(item)) {
        this - item
    } else {
        this + item
    }
}
