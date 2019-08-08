package com.example.myapplication.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.WordListAdapter
import com.example.myapplication.data.Word
import com.example.myapplication.ui.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_words.*

class WordsActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    companion object {
        const val newWordActivityRequestCode = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        recyclerViewInit()
        onClickFab()
        onClickDeleteAll()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word = Word(it.getStringExtra(NewWordActivity.EXTRA_REPLY))
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    private fun recyclerViewInit(){
        val recyclerView = findViewById<RecyclerView>(R.id.words_recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })
    }

    private fun onClickFab(){
        fab.setOnClickListener {
            val intent = Intent(this@WordsActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    private fun onClickDeleteAll(){
        btn_delete_all.setOnClickListener { wordViewModel.deleteAll() }
    }
}
