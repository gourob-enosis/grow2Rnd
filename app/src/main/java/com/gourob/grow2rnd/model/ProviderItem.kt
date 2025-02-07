package com.gourob.grow2rnd.model

data class ProviderItem(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val positionType: String,
    val positionID: Int,
    val pmProviderIDS: List<String>,
    val active: Boolean,
    val provider: Boolean
)

val providerList = listOf(
    ProviderItem(299, "Jeffrey", "Petersen", "Doctor", 1, listOf("TD"), true, true),
    ProviderItem(17, "Nicholas", "Payne", "Doctor", 1, listOf("AAO", "AA"), true, true),
    ProviderItem(22862, "Richard", "Mcmahon", "Doctor", 1, listOf("TDS"), true, true),
    ProviderItem(3555, "Anthony", "Woodward Long Character Count Name", "Hygienist", 2, listOf("HD"), true, true),
    ProviderItem(67, "Deanna", "Sanders", "Hygienist", 2, listOf("TH2"), true, true),
    ProviderItem(30, "Eric", "Mccarthy", "Hygienist", 2, listOf("AB"), true, true),
    ProviderItem(35, "Leslie", "Clark", "Hygienist", 2, listOf("TP"), true, true)
)

