package com.example.myrecyclerlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)


        //create data


        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/forecast/daily?q=Kuala%20Lumpur&appid=9fd7a449d055dba26a982a3220f32aa2"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                println(response)
                var weatherObject = JSONObject(response)
                var weatherArray = weatherObject.getJSONArray("list")
                var dataList = LinkedList<JSONObject>()
                for (i in 0..weatherArray.length()-1){
                    dataList.add(weatherArray.getJSONObject(i))
                }
                var adapter = CustomAdapter(this@MainActivity);
                adapter.list = dataList
                recyclerView.setAdapter(adapter)

            },
            Response.ErrorListener { println("That didn't work!") })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)


    }
}


