package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class Session(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")        val id: Int = 0,
    @ColumnInfo(name = "name")      val name: String = "",
    @ColumnInfo(name = "is_active") val isActive: Boolean = false
)
