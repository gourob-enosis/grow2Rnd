package com.gourob.grow2rnd.model

data class ChairItem(
    val chairId: String,
    val chairName: String,
    val inUse: Boolean,
    val sortOrder: Int
)

val chairList = listOf(
    ChairItem("1", "Doctor1", true, 1),
    ChairItem("2", "Doctor1 Overflow", true, 2),
    ChairItem("3", "Hyg1", true, 3),
    ChairItem("4", "Hyg2", true, 4),
    ChairItem("6", "Doctor2 Overflow", true, 5),
    ChairItem("5", "Doctor2", true, 6)
)