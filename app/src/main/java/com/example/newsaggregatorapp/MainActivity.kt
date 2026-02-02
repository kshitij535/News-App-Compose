package com.example.newsaggregatorapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val MY_API_KEY = "Si8ZvaRroSHLlhX7ApVPxZLfUGpKD2mCoqo7bKL2YsczJ-th"
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var prefs: SharedPreferences


    private lateinit var btnGeneral: Button
    private lateinit var btnTech: Button
    private lateinit var btnSports: Button
    private lateinit var btnBusiness: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerHeadlines)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        prefs = getSharedPreferences("NewsAggregatorPrefs", Context.MODE_PRIVATE)

        btnGeneral = findViewById(R.id.btnGeneral)
        btnTech = findViewById(R.id.btnTech)
        btnSports = findViewById(R.id.btnSports)
        btnBusiness = findViewById(R.id.btnBusiness)


        val syncRequest = PeriodicWorkRequestBuilder<NewsWorker>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "NewsSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )

        val savedCategory = prefs.getString("selected_category", null)
        initCategorySelection(savedCategory)

       btnGeneral.setOnClickListener { selectCategory(btnGeneral, null) }
        btnTech.setOnClickListener { selectCategory(btnTech, "technology") }
        btnSports.setOnClickListener { selectCategory(btnSports, "sports") }
        btnBusiness.setOnClickListener { selectCategory(btnBusiness, "business") }
    }

    private fun initCategorySelection(category: String?) {
        val targetButton = when (category) {
            "technology" -> btnTech
            "sports" -> btnSports
            "business" -> btnBusiness
            else -> btnGeneral
        }
        selectCategory(targetButton, category)
    }

    private fun selectCategory(button: Button, category: String?) {
  val buttons = listOf(btnGeneral, btnTech, btnSports, btnBusiness)
        buttons.forEach {
            it.setBackgroundColor(Color.LTGRAY)
            it.setTextColor(Color.BLACK)
        }

        button.setBackgroundColor(Color.parseColor("#6200EE"))
        button.setTextColor(Color.WHITE)

       prefs.edit().putString("selected_category", category).apply()
        fetchNews(category)
    }

    private fun fetchNews(category: String?) {
        progressBar.visibility = View.VISIBLE
        RetrofitInstance.api.getTopHeadlines(MY_API_KEY, category).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    recyclerView.adapter = NewsAdapter(response.body()!!.news)
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Network Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }
}