package com.example.headsupgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.headsupgame.databinding.ActivityEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class editActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditBinding

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
        apiInterface?.updateTest(binding.nameText.text.toString().toInt(),CelebrityItem(binding.nameText.text.toString(), 4,binding.taboo1Text.text.toString(), binding.taboo2Text.text.toString(),binding.taboo3Text.text.toString()))?.enqueue(object :
            Callback<CelebrityItem> {
            override fun onResponse(call: Call<CelebrityItem>, response: Response<CelebrityItem>) {

                Toast.makeText(this@editActivity, "Updated successfully", Toast.LENGTH_SHORT).show()

                Log.d("MAIN", "Successfully updated")
                println("here is the data entered ${binding.nameText.text} and ${binding.taboo1Text.text} ")

            }
            override fun onFailure(call: Call<CelebrityItem>, t: Throwable) {
                Log.d("MAIN", "Something went wrong!")
            }
        })
    }

}