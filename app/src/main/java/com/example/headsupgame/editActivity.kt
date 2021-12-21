package com.example.headsupgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.headsupgame.databinding.ActivityEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class editActivity : AppCompatActivity() {


    lateinit var binding: ActivityEditBinding

    val APIList = ArrayList<CelebrityItem>()

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.editButton.setOnClickListener {
            updateAPI()

        }

    }

    private fun updateAPI() {

        apiInterface?.updateTest(binding.nameText1.text.toString().toInt(),CelebrityItem(binding.nameText1.text.toString(), 4,binding.TabooText1.text.toString(), binding.TabooText2.text.toString(),binding.TabooText3.text.toString()))?.enqueue(object :
            Callback<CelebrityItem> {
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {

                Log.d("MAIN", "Successfully posted")
                println("here is the data entered ${binding.nameText1.text} and ${binding.nameText1.text} ")


            }
            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                Log.d("MAIN", "Something went wrong!")
            }

        })
    }

}