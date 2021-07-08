package com.goelapplications.collegeapp.listeners

import com.goelapplications.collegeapp.models.CollegeModel
import org.json.JSONArray

interface DataListener {
    fun onRootRetrieved(jsonArray: JSONArray)
    fun onCollegeRetrieved(model: CollegeModel)
    fun onError()
}