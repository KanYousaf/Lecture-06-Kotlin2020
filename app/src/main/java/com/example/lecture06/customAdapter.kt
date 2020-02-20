package com.example.lecture06

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class myCustomAdapter(mContext : Context,
                      var myFav_Season_List: MutableList<String>,
                      var myFavSeasonImage : MutableMap<String,Int>) : ArrayAdapter<String>(mContext,
                                            R.layout.mycustomlistlayout,
                                            myFav_Season_List){

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater= LayoutInflater.from(context)
        val rowView = layoutInflater.inflate(R.layout.mycustomlistlayout,parent,false)

        val textView: TextView = rowView.findViewById(R.id.display_season_names)
        val imageView: ImageView = rowView.findViewById(R.id.display_season_image)
        val ratingBar: RatingBar = rowView.findViewById(R.id.rate_season)

        val season_name= myFav_Season_List[position].toString()
        textView.text = season_name
        imageView.setImageResource(myFavSeasonImage[season_name]!!)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            ratingBar.rating = rating
        }
            return rowView
    }

}