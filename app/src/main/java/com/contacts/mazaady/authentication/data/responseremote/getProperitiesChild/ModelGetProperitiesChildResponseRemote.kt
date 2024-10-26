package com.contacts.mazaady.authentication.data.responseremote.getProperitiesChild

data class ModelGetProperitiesChildResponseRemote(
    val description: Any?,
    val id: Int?,
    val list: Boolean?,
    val name: String?,
    val options: List<Option?>?,
    val other_value: String?,
    val parent: Int?,
    val slug: String?,
    val type: Any?,
    val value: String?
)

data class Option(
    val child: Boolean?,
    val id: Int?,
    val name: String?,
    val parent: Int?,
    val slug: String?
)