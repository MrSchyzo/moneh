package com.example.moneh.ui.model

data class Summary(
    val assets: Assets,
    val liabilities: Liabilities,
    val revenues: Revenues,
    val costs: Costs,
) {
    companion object {
        fun sampleAssets() : Summary =
            Summary(
                liabilities = Liabilities(listOf()),
                revenues = Revenues(listOf()),
                costs = Costs(listOf()),
                assets = Assets((0 until 10).map { 5+it.toDouble() }.map {
                    Asset(id = "Id_$it", amount = it, name = "Name_$it")
                }),
            )
    }
}

@JvmInline
value class Assets(val assets: List<Asset>)
data class Asset(
    val id: String,
    val amount: Double,
    val name: String,
)

@JvmInline
value class Liabilities(val liabilities: List<Liability>)
data class Liability(
    val id: String,
    val amount: Double,
    val name: String,
)

@JvmInline
value class Revenues(val revenues: List<Revenue>)
data class Revenue(
    val id: String,
    val amount: Double,
    val name: String,
)

@JvmInline
value class Costs(val costs: List<Cost>)
data class Cost(
    val id: String,
    val amount: Double,
    val name: String,
)
