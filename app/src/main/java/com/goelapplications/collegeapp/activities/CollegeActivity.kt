package com.goelapplications.collegeapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.goelapplications.collegeapp.R
import com.goelapplications.collegeapp.databinding.ActivityCollegeBinding
import com.goelapplications.collegeapp.models.CollegeModel

class CollegeActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        lateinit var model: CollegeModel
    }

    private lateinit var binding: ActivityCollegeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollegeBinding.inflate(layoutInflater)
        supportActionBar?.title = model.name
        setContentView(binding.root)
        setData()
    }

    private fun setData() {
        binding.collegeName.text = model.name
        binding.domain.text = model.domain
        binding.country.text = model.country
        binding.state.text = getString(R.string.n_a)
        model.state?.let {
            if (it != "null") {
                binding.state.text = it
            }
        }
        setWebsiteLink()
    }

    private fun setWebsiteLink() {
        binding.websiteLink.setOnClickListener {
            val url = model.website
            if (url == null) {
                Toast.makeText(this, R.string.website_error, Toast.LENGTH_SHORT).show()
            }
            url?.let { loadUrl(it) }
        }
    }

    private fun loadUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}