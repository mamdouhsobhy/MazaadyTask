package com.contacts.mazaady.authentication.data.responseremote.properities

data class ModelGetPropritiesResponseRemote(
    val description: String?,
    val id: String?,
    val list: Boolean?,
    val name: String?,
    val options: List<Option?>?,
    var other_value: String = "",
    val parent: Any?,
    val slug: String?,
    val type: String?,
    var value: String = "",
    var isOtherValue:Boolean = false
)

data class Option(
    val child: Boolean?,
    val id: String?,
    val name: String?,
    val parent: Int?,
    val slug: String?
)