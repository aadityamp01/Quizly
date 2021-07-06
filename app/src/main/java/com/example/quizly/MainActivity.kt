package com.example.quizly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.* // very very imp for auto import id's of text views,
import kotlinx.android.synthetic.main.activity_result.*

// buttons add app-gradle plugin to id 'kotlin-android-extensions'

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)

        //WindowCompat.setDecorFitsSystemWindows(window, false)

        bt_start.setOnClickListener{
            if (et_text.text.toString().isEmpty()){
                Toast.makeText(this,"Please Enter Your Name!",Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this@MainActivity,QuizQuestions::class.java)
                //Pass the name through intent using the constant variable which we have created.)
                intent.putExtra(Constants.USER_NAME, et_text.text.toString())

                startActivity(intent)
                finish()
            }
        }

    }

}