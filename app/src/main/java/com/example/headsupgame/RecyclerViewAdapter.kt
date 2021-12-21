package com.example.headsupgame

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupgame.databinding.ItemRowBinding
import kotlinx.android.synthetic.main.item_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
class RecyclerViewAdapter(var context:headsUpPrepActivity,var APIList: ArrayList<CelebrityItem>): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewAdapterView>(){

    private var celebId = 0

     class RecyclerViewAdapterView(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterView {
          return RecyclerViewAdapterView(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent,false))
     }

     override fun onBindViewHolder(holder: RecyclerViewAdapterView, position: Int) {
         var pos = position
          var myTextView1 = APIList[position]
          var myTextView2 = APIList[position]
          var myTextView3 = APIList[position]
          var myTextView4 = APIList[position]
         var myPos = APIList[position]



          holder.binding.apply {
               textView1.text = myTextView1.name
               textView2.text = myTextView2.taboo1
               textView3.text = myTextView3.taboo2
               textView4.text = myTextView4.taboo3



          }

          holder.itemView.updateButton.setOnClickListener {
               Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, editActivity::class.java)
                    context.startActivity(intent)

          }

          holder.itemView.deleteButton.setOnClickListener {

              context.deleteAPI()

//              val intent = Intent(context, editActivity::class.java)
//              celebId = intent.extras!!.getInt("dataId", 0)

             // context.startActivity(intent)


          }


     }
     override fun getItemCount(): Int {
         return APIList.size
     }
}