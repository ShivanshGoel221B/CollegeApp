package com.goelapplications.collegeapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goelapplications.collegeapp.R
import com.goelapplications.collegeapp.models.CollegeModel

class CollegeAdapter (private val context: Context,
                      private val collegeList: ArrayList<CollegeModel>,
                      private val clickListener: ClickListener):
    RecyclerView.Adapter<CollegeAdapter.CollegeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollegeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.college_card, parent, false)
        return CollegeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollegeViewHolder, position: Int) {
        val model = collegeList[position]
        holder.itemView.setOnClickListener { clickListener.onCollegeClicked(model) }
        holder.nameView.text = model.name
    }

    override fun getItemCount(): Int = collegeList.size

    inner class CollegeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.college_name)
    }

    interface ClickListener {
        fun onCollegeClicked (model: CollegeModel)
    }
}