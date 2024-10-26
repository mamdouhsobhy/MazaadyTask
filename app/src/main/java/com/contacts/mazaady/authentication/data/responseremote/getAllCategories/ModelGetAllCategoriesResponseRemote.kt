package com.contacts.mazaady.authentication.data.responseremote.getAllCategories

data class ModelGetAllCategoriesResponseRemote(
    val ads_banners: List<AdsBanner?>?,
    val categories: List<Category?>?,
    val google_version: String?,
    val huawei_version: String?,
    val ios_latest_version: String?,
    val ios_version: String?,
    val statistics: Statistics?
)

data class AdsBanner(
    val duration: Int?,
    val img: String?,
    val media_type: String?
)

data class Category(
    val children: List<Children?>?,
    val circle_icon: String?,
    val description: Any?,
    val disable_shipping: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?
)

data class Children(
    val children: Any?,
    val circle_icon: String?,
    val description: Any?,
    val disable_shipping: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?
)

data class Statistics(
    val auctions: Int?,
    val products: Int?,
    val users: Int?
)