package com.malomnogo.presentation

data class Order(private val list: MutableList<String> = mutableListOf()) {

    fun add(name: String) {
        list.add(name)
    }
}

