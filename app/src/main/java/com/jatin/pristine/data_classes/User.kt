package com.jatin.pristine.data_classes

data class User(
    val id: String,
    val name: String,
    val email: String,
    val description: String,
    val location: String,
    val profession: String,
    val model: String
) {
    constructor() : this("","","","","","","")
}
