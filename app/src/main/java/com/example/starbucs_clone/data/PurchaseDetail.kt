package com.example.starbucs_clone.data

data class PurchaseDetail(
    val trans_id: Int = 0,
    val menu_id: Int = 0,
    val isHot: Boolean = false,
    val size: String = "Grande",
    val extra_espresso: Int = 0,
    val extra_syrup1_id: Int = -1,
    val extra_syrup2_id: Int = -1,
    val extra_whipCream: Boolean = false,
    val extra_iceCream: Boolean = false,
    val extra_coldFoam: Boolean = false,
    val extra_saltedFoam: Boolean = false,
)

val syrupList = listOf(
    "Sauce Caramel", "Syrup Caramel", "Syrup Hazelnut",
    "Syrup Mocha", "Syrup Salted Caramel",
    "Syrup Vanilla", "Sauce Dolce"
)

val historicalPurchaseDetailsList = mutableListOf(
    PurchaseDetail(0,0,false,"Venti"),
    PurchaseDetail(0,1,false,"Tall"),
    PurchaseDetail(2,2,true)
)

val purchaseDetailsList = mutableListOf<PurchaseDetail>()