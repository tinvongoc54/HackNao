package com.app.hack_brain.model.uimodel

data class Unit(
    val unit: String,
    val engViePercent: String,
    val vieEngPercent: String,
    val soundPercent: String,
    val isDisable: Boolean = false
)