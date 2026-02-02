package com.example.newsaggregatorapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class NewsWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("NewsWorker", "Background Sync executed successfully.")
        return Result.success()
    }
}