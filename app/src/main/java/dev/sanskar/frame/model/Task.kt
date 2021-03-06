package dev.sanskar.frame.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Task(
    val item: String,
    var status: String,
    val timestamp: Long,
    @Exclude @get: Exclude var id: String
) {
    constructor() : this("", "", 0, "")
}
