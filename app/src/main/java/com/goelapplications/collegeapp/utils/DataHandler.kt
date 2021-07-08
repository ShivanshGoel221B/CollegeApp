package com.goelapplications.collegeapp.utils

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.goelapplications.collegeapp.listeners.DataListener
import com.goelapplications.collegeapp.models.CollegeModel
import com.goelapplications.collegeapp.utils.Constants.COUNTRY
import com.goelapplications.collegeapp.utils.Constants.DOMAINS
import com.goelapplications.collegeapp.utils.Constants.NAME
import com.goelapplications.collegeapp.utils.Constants.STATE
import com.goelapplications.collegeapp.utils.Constants.URL
import com.goelapplications.collegeapp.utils.Constants.WEB_PAGES
import com.goelapplications.collegeapp.utils.OfflineModels.OFFLINE_MODELS
import org.json.JSONArray
import org.json.JSONException


class DataHandler (context: Context, private val listener: DataListener) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getOfflineList () {
        OFFLINE_MODELS.forEach {
            listener.onCollegeRetrieved(model = it)
        }
    }

    fun getRoot () {
        val jsonListener = Response.Listener<JSONArray> {
            try {
                listener.onRootRetrieved(it)
            } catch (e: JSONException) {
                Log.d("CollegeAppRootError", e.message.toString())
                listener.onError()
            }
        }
        val errorListener = Response.ErrorListener {
            listener.onError()
            Log.d("CollegeAppVolleyError", it.toString())
        }

        val request = JsonArrayRequest(
            Request.Method.GET, URL,
            null, jsonListener, errorListener)
        request.retryPolicy = DefaultRetryPolicy(
            100000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(request)
    }

    fun getAllColleges (jsonArray: JSONArray) {
        val size = jsonArray.length()
        for (i in 20 until size) {
            try {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString(NAME)
                val domain = jsonObject.getJSONArray(DOMAINS).getString(0)
                val website = jsonObject.getJSONArray(WEB_PAGES).getString(0)
                val state = jsonObject.getString(STATE)
                val country = jsonObject.getString(COUNTRY)
                val model = CollegeModel(domain = domain, name = name,
                    website = website, state = state , country = country)
                listener.onCollegeRetrieved(model)
            } catch (e: JSONException) {
                Log.d("CollegeAppListError", e.message.toString())
                listener.onError()
                continue
            }
        }
    }
}