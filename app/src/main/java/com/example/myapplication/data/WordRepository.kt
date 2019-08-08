package com.example.myapplication.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    // @WorkerThread annotation, to mark that this method needs to be called from a non-UI thread.
    @WorkerThread
    // suspend modifier to tell the compiler that this needs to be called from a coroutine or another suspending function.
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    @WorkerThread
    fun deleteAll() {
        wordDao.deleteAll()
    }
}