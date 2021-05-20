package com.study.coroutinedemo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class KotlinActivity : AppCompatActivity() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val progressBar by lazy {
        findViewById<ProgressBar>(R.id.progressBar)
    }
    private val tvMessage by lazy {
        findViewById<TextView>(R.id.tvMessage)
    }
    private val btnWrite by lazy {
        findViewById<Button>(R.id.btnWrite)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_activity_layout)

        btnWrite.setOnClickListener {
            performWrite()

            // or

//            performWrite2()
        }
    }

    private fun performWrite2() {
        uiScope.launch {
            //onPreExecute
            progressBar.visibility = View.VISIBLE

            val result = doInBackground()

            //onPostExecute
            progressBar.visibility = View.GONE

            if (result)
                tvMessage.text = "Success"
            else
                tvMessage.text = "Failure"
        }
    }

    private suspend fun doInBackground(): Boolean = withContext(Dispatchers.IO) {
//        delay(3000)
        return@withContext VideoUtil.writeVideoToStorage(this@KotlinActivity)
    }

    private fun performWrite() = uiScope.launch {
        // onPreExecute
        progressBar.visibility = View.VISIBLE
        
        val result = doInBackground()

        // onPostExecute
        progressBar.visibility = View.GONE

        if (result)
            tvMessage.text = "Success"
        else
            tvMessage.text = "Failure"
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}