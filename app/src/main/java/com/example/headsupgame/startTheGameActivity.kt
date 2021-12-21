package com.example.headsupgame

import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.headsupgame.databinding.ActivityHeadsUpPrepBinding
import com.example.headsupgame.databinding.ActivityStartTheGameBinding


class startTheGameActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartTheGameBinding
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var list: MutableList<List<String>>


    private var size = 0
    private var randoms = arrayListOf<Int>()
    private var idx = 0
    private var score = 0


    private var timerFinish = false
    private var timerLeft = 4000L

    var canPlay = true
    private var timeLeft = 60000L
    private lateinit var countDownTimer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartTheGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTimer()


        this.title = "Time: $timeLeft"

        sharedPreferences = getSharedPreferences("Wasan_Heads_up_game", MODE_PRIVATE)
        val edit = sharedPreferences.edit()


        list = mutableListOf()
        size = intent.getIntExtra("size", -1)

        for (i in 0 until size) {
            intent.getStringExtra("c$i")?.split("|||")?.let { list.add(it) }
            val edit = sharedPreferences.edit()
            edit.putString("c$i", "${list[i][0]}|||${list[i][1]}|||${list[i][2]}|||${list[i][3]}")
        }
        edit.apply()
        randoms = (0..size).shuffled().take(size) as ArrayList<Int>
        for (i in 0 until size)
            edit.putInt("r$i", randoms[i])
        edit.apply()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (size > idx && canPlay && timerFinish) {
            if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
                val rnd = sharedPreferences.getInt("r$idx", -1)
                val selectedItem = sharedPreferences.getString("c$rnd", "")!!.split("|||")
                binding.name.text = selectedItem[0]
                binding.t1.text = selectedItem[1]
                binding.t2.text = selectedItem[2]
                binding.t3.text = selectedItem[3]
                binding.linearLayout.visibility = View.VISIBLE
                binding.linear1.visibility = View.GONE

            } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
                idx++
                score++
                binding.linearLayout.visibility = View.GONE
                binding.linear1.visibility = View.VISIBLE
                binding.score.text = "Your score: $size/$score"
            }
        } else {
            binding.txt.text = "Game finish"
            binding.linear1.visibility = View.VISIBLE
            binding.score.text = "Your score: $size/$score"
        }
    }


    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timerLeft, 1000) {
            override fun onTick(p0: Long) {
                timerLeft = p0
                updateSeconds()
            }

            override fun onFinish() {
                binding.linear0.visibility = View.GONE
                binding.linear1.visibility = View.VISIBLE
                timerFinish = true
                startTime()
                binding.score.text = "Your score: $size/$score"
            }
        }.start()
    }

    private fun updateSeconds() {
        binding.seconds.text = "${timerLeft / 1000} Seconds"
    }

    private fun startTime() {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(p0: Long) {
                timeLeft = p0
                updateCountDownTimer()
            }

            override fun onFinish() {
                binding.linearLayout.visibility = View.GONE
                binding.linear1.visibility = View.VISIBLE
                canPlay = false
                binding.score.text = "Your score: $size/$score"
            }
        }.start()
    }

    private fun updateCountDownTimer() {
        this.title = "Time: ${timeLeft / 1000}"
    }
}