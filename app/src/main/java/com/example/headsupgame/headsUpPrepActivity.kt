package com.example.headsupgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupgame.databinding.ActivityHeadsUpPrepBinding
import com.example.headsupgame.databinding.ItemRowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class headsUpPrepActivity : AppCompatActivity() {

    lateinit var binding: ActivityHeadsUpPrepBinding

    lateinit var myRV: RecyclerView

    val APIList = ArrayList<CelebrityItem>()

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    var rowSelected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadsUpPrepBinding.inflate(layoutInflater)

        setContentView(binding.root)

        myRV = findViewById(R.id.rvMain)
        myRV.layoutManager = LinearLayoutManager(this)

        getAPI()

        binding.goToAddCelebrityActivityButton.setOnClickListener {
            val intent = Intent(this, addCelebrityActivity::class.java)
            startActivity(intent)
            myRV.adapter = RecyclerViewAdapter(this@headsUpPrepActivity, APIList)
            myRV.adapter?.notifyDataSetChanged()
        }
        binding.goBackToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            myRV.adapter = RecyclerViewAdapter(this@headsUpPrepActivity, APIList)
            myRV.adapter?.notifyDataSetChanged()
        }

    }


    fun getAPI() {

        Log.d("respoNoE", "${apiInterface}")
        val dataRecieved = apiInterface?.getTest()
        println("here is the data received: $dataRecieved ")

        dataRecieved?.enqueue(object : Callback<ArrayList<CelebrityItem>> {
            override fun onResponse(
                call: Call<ArrayList<CelebrityItem>>,
                response: Response<ArrayList<CelebrityItem>>
            ) {
                val myResponse = response.body()
                if (myResponse != null) {
                    for (i in myResponse) {
                        APIList.add(i)
//

                        myRV.adapter = RecyclerViewAdapter(this@headsUpPrepActivity, APIList)
                        myRV.adapter?.notifyDataSetChanged()
                        rowSelected = i.pk
                        println("here is the APIList $APIList")
                        println("here is the i : $i") //i is the entire row
                        println("here is the i.pk : ${i.pk}") //i.pk is the pk for individual row
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<CelebrityItem>>, t: Throwable) {
                Log.d("response", "failed to get data , ${t.message}")
            }
        })
    }

    fun deleteAPI() {

        Log.d("outDeleteAPI", "$rowSelected")
        apiInterface?.deleteTest(rowSelected)?.enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                myRV.adapter = RecyclerViewAdapter(this@headsUpPrepActivity, APIList)
                myRV.adapter?.notifyDataSetChanged()

                Toast.makeText(this@headsUpPrepActivity, "Deleted Successfully", Toast.LENGTH_SHORT)
                    .show()
                Log.d("inDeleteAPI", "$rowSelected")
                Log.d("MAIN", "Successfully Deleted")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("MAIN", "Something went wrong!")
            }
        })
    }
}