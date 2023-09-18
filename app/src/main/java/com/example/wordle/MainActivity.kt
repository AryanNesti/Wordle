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
        var wordSize = 4
        var guess = 1

        findViewById<TextView>(R.id.solutionTextView).text = word

        button.setOnClickListener{
            if (editText.text.length != 4) {
                Toast
                    .makeText(this, "Please enter a 4-letter word!", Toast.LENGTH_LONG)
                    .show()
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
                    Log.v(check.toString(), "passed")
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
            guess1.text = "____"
            guess2.text = "____"
            guess3.text = "____"
            correct.visibility = View.INVISIBLE
            wordle.visibility = View.VISIBLE
            guess = 1
            word = FourLetterWordList.getRandomFourLetterWord()
            findViewById<TextView>(R.id.solutionTextView).text = word
            button.visibility = View.VISIBLE
            findViewById<TextView>(R.id.solutionTextView).visibility = View.INVISIBLE
            reset.visibility = View.INVISIBLE
        }
    }
}