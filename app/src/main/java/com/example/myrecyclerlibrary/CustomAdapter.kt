package com.example.myrecyclerlibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class CustomAdapter (val context : Context) : Adapter<CustomAdapter.CustomViewHolder>() {
    var list = LinkedList<JSONObject>()

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var titleTextView : TextView = itemView.findViewById(R.id.titleTextView)
        var subtitleTextView : TextView = itemView.findViewById(R.id.subtitleTextView)
        var imageView : ImageView = itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val context = parent.context
        var inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // For each row, show the item inside the list.
        // list.get(position) -> weatherObject
        var dt = list.get(position).getLong("dt")

        // Unix timestamp (in seconds)
        // Convert timestamp to milliseconds
        val timestampInMilliseconds = dt * 1000L

        // Create a Date object from the timestamp
        val dateTime = Date(timestampInMilliseconds)

        // Create a SimpleDateFormat object with a custom date and time format
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        // Format the date using the SimpleDateFormat object
        val dateFormattedTime = dateFormat.format(dateTime)

        // Create Calendar object and set its time to the Unix timestamp
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(timestampInMilliseconds)

        // Get the day of the week as a human-readable string
        val dayOfWeekName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

        holder.titleTextView.text = "$dayOfWeekName \n$dateFormattedTime"

        var temperature = list.get(position).getJSONObject("temp").getDouble("day")- 273.15
        holder.subtitleTextView.text = String.format("%.2f C",temperature)

        var iconId = list.get(position).getJSONArray("weather").getJSONObject(0).getString("icon")

        var imageUrl = "https://openweathermap.org/img/wn/$iconId@2x.png"
        Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}
