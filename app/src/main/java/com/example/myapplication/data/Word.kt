package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word (
    val word: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}