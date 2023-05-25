package com.example.airindia_qfon.Adepter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.airindia_qfon.Modelclass.Daum
import com.example.airindia_qfon.Modelclass.Root
import com.example.airindia_qfon.R


class AdepterAirlines(var listairlines: List<Daum>, var context: Context) :
    RecyclerView.Adapter<AdepterAirlines.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_airlines = view.findViewById<ImageView>(R.id.img_airlines)
        val txt_id = view.findViewById<TextView>(R.id.txt_id)
        val txt_name = view.findViewById<TextView>(R.id.txt_name)
        val txt_trips = view.findViewById<TextView>(R.id.txt_trips)
        val txt_slogan = view.findViewById<TextView>(R.id.txt_slogan)
        val txt_country = view.findViewById<TextView>(R.id.txt_country)
        val txt_head_quater = view.findViewById<TextView>(R.id.txt_head_quater)
        val txt_website = view.findViewById<TextView>(R.id.txt_website)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context =parent.context
        val mView = LayoutInflater.from(context).inflate(R.layout.raw_airlines,parent,false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataAirlines =listairlines[position]

        holder.txt_id.text = dataAirlines.id
        holder.txt_name.text = dataAirlines.name
        holder.txt_trips.text = dataAirlines.trips.toString()
        holder.txt_slogan.text = "Slogan : "+ dataAirlines.airline.get(0).slogan
        holder.txt_country.text =  dataAirlines.airline.get(0).country
        holder.txt_head_quater.text =  dataAirlines.airline.get(0).headQuaters
        holder.txt_website.text =  dataAirlines.airline.get(0).website
        Glide.with(context).load(dataAirlines.airline.get(0).logo).into(holder.img_airlines)


    }

    override fun getItemCount(): Int {
        return listairlines.size
    }
}