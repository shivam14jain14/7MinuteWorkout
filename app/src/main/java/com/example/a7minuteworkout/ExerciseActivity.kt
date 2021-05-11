package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.net.Uri
import android.os.CountDownTimer
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList




class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    //TODO(Step 1 - Adding a variables for the 10 seconds REST timer.)
    //START
    private var restTimer: CountDownTimer? =
        null // Variable for Rest Timer and later on we will initialize it.
    private var restProgress =
        0
    private var restTimerDuration :Long=11
    private var exerciseTimer: CountDownTimer? =
        null // Variable for Rest Timer and later on we will initialize it.
    private var exerciseProgress =
        0
    // Variable for timer progress. As initial value the rest progress is set to 0. As we are about to start.

    private var exerciseTimerDuration:Long=31
    private var exerciseList:ArrayList<ExerciseModel>? =null
    private var currentExercisePosition=-1


    private var tts:TextToSpeech? = null



    private var player:MediaPlayer? =null



    private var exerciseAdapter:ExerciseStatusAdapter? =null
//    FOR SOUND
    //END

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Navigate the activity on click on back button of action bar.
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()

        }
        //TODO(Step 4 - Calling the function to make it visible on screen.)-->
        //START


        tts  = TextToSpeech(this,this)
//        TEXT TO SPEECH OBJECT
        exerciseList=Constants.defaultExerciseList()
        setupRestView() // REST View is set in this function
//
        setupExerciseStatusRecyclerView()
    }


    public override fun onDestroy() {
    if (restTimer!= null) {
        restTimer!!.cancel()
        restProgress = 0
    }
    if (exerciseTimer!= null) {
        exerciseTimer!!.cancel()
        exerciseProgress = 0
    }
    if(tts!=null)
    {
        tts!!.stop()
        tts!!.shutdown()
    }
        if(player!=null)
            player!!.stop()

    super.onDestroy()
}

    override fun onInit(status: Int) {

        // TODO (Step 5 - After variable initializing set the language after a "success"ful result.)
        // START
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
        // END
    }

    private fun setupRestView() {

        try {
            val soundURI =
                Uri.parse("android.resource://com.example.a7minuteworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player!!.isLooping = false // Sets the player to be looping or non-looping.
            player!!.start() // Starts Playback.
        } catch (e: Exception) {
            e.printStackTrace()
        }
          llRestView.visibility=View.VISIBLE
        llExerciseView.visibility=View.GONE



        /**
         * Here firstly we will check if the timer is running the and it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
speakOut("GET READY FOR THE NEXT EXERCISE " +exerciseList!![currentExercisePosition+1].getName())
        upcomingExerciseName.text=exerciseList!![currentExercisePosition+1].getName()

        // This function is used to set the progress details.
        setRestProgressBar()
    }
    // END

    //TODO(Step 2 - Setting up the 10 seconds timer for rest view and updating it continuously.)-->
    //START
    /**
     * Function is used to set the progress of timer using the progress
     */
    private fun setRestProgressBar() {

        progressBar.progress = restProgress // Sets the current progress to the specified value.
        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        restTimer = object : CountDownTimer(restTimerDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // It is increased by 1
                progressBar.progress =  restTimerDuration.toInt()- restProgress // Indicates progress bar progress
                tvTimer.text =
                    (restTimerDuration.toInt() - restProgress).toString()  // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {
                // When the 10 seconds will complete this will be executed.
                     currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)

                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }

        }.start()
    }
    //END

    //TODO(Step 5 - Destroying the timer when closing the activity or app.)-->
    //START
    /**
     * Here in the Destroy function we will reset the rest timer if it is running.
     */


    private fun setupExerciseView()
    {
        llRestView.visibility=View.GONE
        llExerciseView.visibility=View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
//        TO SET THE IMAGE
        tvExerciseName.text=exerciseList!![currentExercisePosition].getName()

        speakOut(exerciseList!![currentExercisePosition].getName())


        // This function is used to set the progress details.
        setExerciseProgressBar()



//        TO SET THE TEXT OF THE EXERCISE


    }

    private fun setExerciseProgressBar() {

        progressBarExercise.progress = exerciseProgress // Sets the current progress to the specified value.
        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++ // It is increased by 1
                progressBarExercise.progress = exerciseTimerDuration.toInt() - exerciseProgress // Indicates progress bar progress
                tvExerciseTimer.text =
                    (exerciseTimerDuration.toInt() - exerciseProgress).toString()  // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {
                // When the 10 seconds will complete this will be executed.

                if(currentExercisePosition<11) {
                          exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()

                }
                else
                {
                          finish()
                    val intent=Intent(this@ExerciseActivity,PleaseWorkThisTime::class.java)
                    startActivity(intent)


                }
            }

        }.start()
    }

    private fun speakOut(text:String)
    {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }



    private fun setupExerciseStatusRecyclerView()
    {
        rvExerciseStatus.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//TO DEFINE THAT HOW THE CIRCLES WILL APPEAR IN THE LAYOUT
//        layoutManager responisble for the layout policy of the recyclerview

        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter=exerciseAdapter
    }



    private fun customDialogForBackButton()
    {
        val customDialog=Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.tvYes.setOnClickListener{
            finish()
            customDialog.dismiss()
        }
        customDialog.tvNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()

    }
    //END
}

//override fun onInit(status: Int) {
//    if(status == TextToSpeech.SUCCESS) {
//        val result = tts!!.setLanguage(Locale.US)
//        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
//            Log.e("TTS", "Language specified is not supported !")
//    }
//    else
//        Log.e("TTS","Initialization failed !")
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//}
//
//    //TODO(Step 3 - Setting up the Get Ready View with 10 seconds of timer.)-->
//    //START
//    /**
//     * Function is used to set the timer for REST.
//     */

