package com.malomnogo.testweatherproject

data class Order(private val list: MutableList<String> = mutableListOf()) {

    fun add(name: String) {
        list.add(name)
    }
}