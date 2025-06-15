package com.dkat.wordbook.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
data class Language(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_language_id")  override val id: Int = 0,
    @ColumnInfo(name = "language_name") override val name: String = "",
) : NamedEntity
