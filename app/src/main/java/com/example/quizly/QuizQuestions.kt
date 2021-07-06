package com.example.quizly

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import kotlinx.android.synthetic.main.activity_quizly_questions.*
import kotlinx.android.synthetic.main.activity_quizly_questions.view.*

class QuizQuestions : AppCompatActivity(), View.OnClickListener{

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0

    // TODO (STEP 3: Create a variable for getting the name from intent.)
    // START
    private var mUserName: String? = null
    // END
/*
    //All the text views found from the activity
    private val tvQuestion: TextView? = findViewById(R.id.tv_question)
    private val ivImage: ImageView? = findViewById(R.id.iv_flag)
    private val tvOption1: TextView? = findViewById(R.id.option1)
    private val tvOption2: TextView? = findViewById(R.id.option2)
    private val tvOption3: TextView? = findViewById(R.id.option3)
    private val tvOption4: TextView? = findViewById(R.id.option4)
    private val btnSubmit: Button? = findViewById(R.id.bt_submit)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizly_questions)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // TODO (STEP 4: Get the NAME from intent and assign it the variable.)
        // START
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        // END

        mQuestionsList = Constants.getQuestions()

        // Log.i ("Question Size", "${questionList.size}")       // Used this to get size of questions in Logcat

        setQuestion()

        // One wey to create setOnClickListener method
        option1.setOnClickListener(this)
        option2.setOnClickListener(this)
        option3.setOnClickListener(this)
        option4.setOnClickListener(this)

        bt_submit.setOnClickListener(this)
    }

    private fun setQuestion(){
        val question: Question = mQuestionsList!![mCurrentPosition -1]

        defaultOptionView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            bt_submit.setText(R.string.finish)
        } else {
            bt_submit.setText(R.string.submit)
        }

        progressBar.progress = mCurrentPosition*10


        tv_progressBar.text  = ("$mCurrentPosition" + "/" +  progressBar.max/10)
        //tv_progressBar.text  = (mCurrentPosition + (R.string.divide)+  progressBar.max).toString()


        tv_question.text = question.question
        iv_flag.setImageResource(question.Image)

        option1.text = question.optionOne
        option2.text = question.optionTwo
        option3.text = question.optionThree
        option4.text = question.optionFour



    }

    private fun defaultOptionView(){
        val options = ArrayList<TextView>()

        options.add(0,option1)
        options.add(1,option2)
        options.add(1,option3)
        options.add(1,option4)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizQuestions,
                R.drawable.default_option_border_bg
            )
        }

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.option1->{
                selectOptionView(option1,1)
            }
            R.id.option2->{
                selectOptionView(option2,2)
            }
            R.id.option3->{
                selectOptionView(option3,3)
            }
            R.id.option4->{
                selectOptionView(option4,4)
            }

            R.id.bt_submit->{
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {
                            //Toast.makeText(this, "You've successfully completed the quiz", Toast.LENGTH_SHORT).show()

                            // TODO (STEP 5: Now remove the toast message and launch the result screen which we have created and also pass the user name and score details to it.)
                            // START
                            val intent =
                                Intent(this@QuizQuestions, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            // END
                        }
                    }
                }else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else{
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        bt_submit.setText(R.string.finish)
                    } else {
                        bt_submit.setText(R.string.goNextQ)


                    }

                    mSelectedOptionPosition = 0

                }
            }
        }

    }

    private fun answerView(answer: Int,drawableView: Int){
        when(answer){
            1 -> {
                option1.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2-> {
                option2.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 ->{
                option3.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4->{
                option4.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    // This function is for action when we click on options so it resets the default and makes it dark
    private fun selectOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)   // Set Typeface to bold we use this function
        // not assigning here like above

        tv.background = ContextCompat.getDrawable(
            this@QuizQuestions,
            R.drawable.selected_option_border_bg
        )

    }

}
