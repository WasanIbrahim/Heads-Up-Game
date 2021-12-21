package com.example.headsupgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupgame.databinding.ActivityAddCelebrityBinding
import com.example.headsupgame.databinding.ActivityHeadsUpPrepBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class addCelebrityActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCelebrityBinding


    val APIList = ArrayList<CelebrityItem>()

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCelebrityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addButton.setOnClickListener {
            postAPI()
            binding.nameText.text = null
            binding.taboo1Text.text = null
            binding.taboo2Text.text = null
            binding.taboo3Text.text = null


        }

        binding.goBackToHeadsUpPrepButton.setOnClickListener {
            val intent = Intent(this, headsUpPrepActivity::class.java)
            startActivity(intent)
        }
    }

    fun postAPI() {
        apiInterface?.postTest(CelebrityItem(binding.nameText.text.toString(), 80,binding.taboo1Text.text.toString(),binding.taboo2Text.text.toString(),binding.taboo3Text.text.toString()))?.enqueue(object :
            Callback<CelebrityItem> {
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {

                Log.d("MAIN", "Successfully posted")

            }
            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                Log.d("MAIN", "Something went wrong!")
            }

        })

    }

}