package com.goelapplications.collegeapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.goelapplications.collegeapp.R
import com.goelapplications.collegeapp.adapters.CollegeAdapter
import com.goelapplications.collegeapp.databinding.ActivityMainBinding
import com.goelapplications.collegeapp.listeners.DataListener
import com.goelapplications.collegeapp.models.CollegeModel
import com.goelapplications.collegeapp.utils.DataHandler
import org.json.JSONArray

class MainActivity : AppCompatActivity(), DataListener, CollegeAdapter.ClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: DataHandler
    private lateinit var rootArray: JSONArray
    private lateinit var collegeList: ArrayList<CollegeModel>
    private lateinit var collegeAdapter: CollegeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = DataHandler(this, this)
        createRecyclerView()
        handler.getOfflineList()
        handler.getRoot()
    }

    private fun createRecyclerView() {
        collegeList = ArrayList()
        collegeAdapter = CollegeAdapter(this, collegeList, this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = collegeAdapter
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun onCollegeClicked(model: CollegeModel) {
        CollegeActivity.model = model
        startActivity(Intent(this, CollegeActivity::class.java))
    }

    override fun onRootRetrieved(jsonArray: JSONArray) {
        rootArray = jsonArray
        handler.getAllColleges(rootArray)
    }

    override fun onCollegeRetrieved(model: CollegeModel) {
        collegeList.add(model)
        collegeAdapter.notifyItemInserted(collegeList.size)
    }

    override fun onError() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show()
    }
}