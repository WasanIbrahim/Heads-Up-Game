package com.example.headsupgame

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headsupgame.databinding.ItemRowBinding
import kotlinx.android.synthetic.main.item_row.view.*


class RecyclerViewAdapter(var context:headsUpPrepActivity,var APIList: ArrayList<CelebrityItem>): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewAdapterView>() {


    class RecyclerViewAdapterView(val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterView {
        return RecyclerViewAdapterView(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterView, position: Int) {
        var myTextView1 = APIList[position]
        var myTextView2 = APIList[position]
        var myTextView3 = APIList[position]
        var myTextView4 = APIList[position]



        holder.binding.apply {
            textView1.text = myTextView1.name
            textView2.text = myTextView2.taboo1
            textView3.text = myTextView3.taboo2
            textView4.text = myTextView4.taboo3


        }

        holder.itemView.updateButton.setOnClickListener {
            val intent = Intent(context, editActivity::class.java)
            context.startActivity(intent)

        }

        holder.itemView.deleteButton.setOnClickListener {

            context.deleteAPI()

        }


    }

    override fun getItemCount(): Int {
        return APIList.size
    }
}