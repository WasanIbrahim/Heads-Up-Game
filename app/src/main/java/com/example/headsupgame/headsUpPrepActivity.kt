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

     var celebID = 0

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

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
        }
        binding.goBackToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }


    fun getAPI(){

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
                        celebID= i.pk
                        intent.putExtra("celebID", celebID)
                        startActivity(intent)
                        Log.d("getID", "$celebID")
//                        intent.putExtra("dataId", i.pk)
                        //println("here is the APIList $APIList")
                        myRV.adapter = RecyclerViewAdapter(this@headsUpPrepActivity,APIList)
                        myRV.adapter?.notifyDataSetChanged()
                        println("here is the APIList $APIList")

                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<CelebrityItem>>, t: Throwable) {
                Log.d("response", "failed to get data , ${t.message}")
            }
        })
    }

    fun deleteAPI(){

        var celeb2 =0

        celeb2=intent.extras!!.getInt("celebID",0)
        apiInterface?.deleteTest(celeb2)?.enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                //Toast.makeText(context, "Item row deleted", Toast.LENGTH_SHORT).show()
                Log.d("MAIN", "Successfully Deleted")
                Log.d("deleteID", "$celebID")
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("MAIN", "Something went wrong!")
            }

        })
    }

}