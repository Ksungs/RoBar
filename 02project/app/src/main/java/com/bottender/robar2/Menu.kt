package com.bottender.robar2

data class Menu(
    val name: String,
    val water: Int,
    val cola: Int,
    val cider: Int,
    val orange: Int,
    val isDefault: Boolean = false // 기본 메뉴 식별을 위한 속성 추가
) {
    fun calculatePrice(): Int {
        return water * 10 + cola * 15 + cider * 20 + orange * 25
    }
}
