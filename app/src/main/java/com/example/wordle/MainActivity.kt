package com.example.wordle

import android.R.id.text2
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        var word = FourLetterWordList.getRandomFourLetterWord()

        val editText = findViewById<EditText>(R.id.editText)

        val guess1 = findViewById<TextView>(R.id.guess1TextView)
        val guess2 = findViewById<TextView>(R.id.guess2TextView)
        val guess3 = findViewById<TextView>(R.id.guess3TextView)
        val reset = findViewById<Button>(R.id.resetButton)
        val correct = findViewById<ImageView>(R.id.imageView)
        val wordle = findViewById<TextView>(R.id.textView)
        val fiveLetters = findViewById<Button>(R.id.fiveButton)
        val fourLetters = findViewById<Button>(R.id.fourButton)
        val streaks = findViewById<TextView>(R.id.streaks)
        var streak = 0
        var wordSize = 4
        var guess = 1

        findViewById<TextView>(R.id.solutionTextView).text = word
        Log.v("Answer", word)
        button.setOnClickListener{
            fiveLetters.visibility = View.INVISIBLE
            fourLetters.visibility = View.INVISIBLE
            if (editText.text.length != wordSize) {
                Toast.makeText(this, "Please enter a 4-letter word!", Toast.LENGTH_LONG).show()
                editText.text.clear()
                return@setOnClickListener
            }
            val string = editText.text.toString().uppercase()
            val spannableString = SpannableString(string)
            editText.setText("");
            var check = 0
            for(i in 0..wordSize-1){
                var color = Color.BLACK
                if(string[i] == word[i]){
                    color = Color.GREEN
                    check++
                } else if(string[i] in word){
                    color = Color.YELLOW
                } else {
                    color = Color.RED
                }
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                if(check == 4){
                    findViewById<TextView>(R.id.textView).visibility = View.INVISIBLE
                    correct.visibility = View.VISIBLE
                    button.visibility = View.INVISIBLE
                    findViewById<TextView>(R.id.solutionTextView).visibility = View.VISIBLE
                    reset.visibility = View.VISIBLE
                    streak++
                    streaks.text = "Streaks: ${streak}"
                    fiveLetters.visibility = View.VISIBLE
                    fourLetters.visibility = View.VISIBLE
                    this.hideKeyboard()
                } else if(guess == 3){
                    fiveLetters.visibility = View.VISIBLE
                    fourLetters.visibility = View.VISIBLE
                    streak = 0
                    streaks.text = "Streaks: ${streak}"
                    Toast.makeText(this, "Seems you didn't get it, Better Luck Next Time", Toast.LENGTH_LONG)
                    this.hideKeyboard()
                }
            }
            if(guess == 1){
                guess1.setText(spannableString, TextView.BufferType.SPANNABLE)
                guess++
            } else if(guess == 2){
                guess2.setText(spannableString, TextView.BufferType.SPANNABLE)
                guess++
            } else {
                guess3.setText(spannableString, TextView.BufferType.SPANNABLE)
                button.visibility = View.INVISIBLE
                findViewById<TextView>(R.id.solutionTextView).visibility = View.VISIBLE
                reset.visibility = View.VISIBLE
            }
        }
        reset.setOnClickListener {
            if(wordSize == 4){
                guess1.text = "____"
                guess2.text = "____"
                guess3.text = "____"
                word = FourLetterWordList.getRandomFourLetterWord()
            } else if(wordSize == 5) {
                guess1.text = "_____"
                guess2.text = "_____"
                guess3.text = "_____"
                word = FiveLetterWordList.getRandomFourLetterWord()
            }
            correct.visibility = View.INVISIBLE
            wordle.visibility = View.VISIBLE
            guess = 1
            Log.v("Answer", word)
            findViewById<TextView>(R.id.solutionTextView).text = word
            button.visibility = View.VISIBLE
            findViewById<TextView>(R.id.solutionTextView).visibility = View.INVISIBLE
            reset.visibility = View.INVISIBLE
        }
        fourLetters.setOnClickListener {
            wordSize = 4
            guess1.text = "____"
            guess2.text = "____"
            guess3.text = "____"
            correct.visibility = View.INVISIBLE
            wordle.visibility = View.VISIBLE
            guess = 1
            word = FourLetterWordList.getRandomFourLetterWord()
            Log.v("Answer", word)
            findViewById<TextView>(R.id.solutionTextView).text = word
            button.visibility = View.VISIBLE
            findViewById<TextView>(R.id.solutionTextView).visibility = View.INVISIBLE
            reset.visibility = View.INVISIBLE
        }
        fiveLetters.setOnClickListener {
            wordSize = 5
            guess1.text = "_____"
            guess2.text = "_____"
            guess3.text = "_____"
            correct.visibility = View.INVISIBLE
            wordle.visibility = View.VISIBLE
            guess = 1
            word = FiveLetterWordList.getRandomFourLetterWord()
            Log.v("Answer", word)
            findViewById<TextView>(R.id.solutionTextView).text = word
            button.visibility = View.VISIBLE
            findViewById<TextView>(R.id.solutionTextView).visibility = View.INVISIBLE
            reset.visibility = View.INVISIBLE
        }
    }
    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}