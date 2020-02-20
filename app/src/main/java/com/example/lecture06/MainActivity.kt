package com.example.lecture06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val myFav_Season_List: MutableList<String> = mutableListOf(
        "Silicon Valley",
        "Game of Thrones",
        "Big Bang Theory",
        "Prison Break",
        "Citizen Khan",
        "Divinci Demons",
        "Mr. Robot",
        "House of Cards",
        "Sherlock Holmes",
        "The Witcher")

    private val myFavSeasonImage : MutableMap<String,Int> = mutableMapOf(
        "Silicon Valley" to R.drawable.siliconvalley,
        "Game of Thrones" to R.drawable.gameofthrones,
        "Big Bang Theory" to R.drawable.bigbangtheory,
        "Prison Break" to R.drawable.prisonbreak,
        "Citizen Khan" to R.drawable.citizenkhan,
        "Divinci Demons" to R.drawable.divincidemons,
        "Mr. Robot" to R.drawable.mrrobot,
        "House of Cards" to R.drawable.houseofcards,
        "Sherlock Holmes" to R.drawable.sherlockholmes,
        "The Witcher" to R.drawable.witcher)

    private var season_list_adapter: ArrayAdapter<String>? = null
    private var custom_season_list_adapter : myCustomAdapter?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //use simple list view technique to display or remove list of seasons
//        season_List_With_PreDefined_Adapter()

        //use custom list view technique to display or remove list of seasons
        season_List_With_Custom_Adapter()

        //remove season item by using Long Click Listener
        remove_Season_ListItem()


    }

    fun season_List_With_PreDefined_Adapter(){
        //set up array adapter for the list
        season_list_adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            myFav_Season_List
        )
        //attach adapter to the list
        displaySeasonList.adapter = season_list_adapter

        //use anonymous class of list to select the item from list and move to another activity
        displaySeasonList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item_name = parent?.getItemAtPosition(position).toString()
                val details_intent = Intent(this@MainActivity, DetailsActivity::class.java)
                details_intent.putExtra("season_item_name", item_name)
                startActivity(details_intent)
            }
        }
    }

    fun remove_Season_ListItem(){
        displaySeasonList.onItemLongClickListener = object : AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ): Boolean {

                myFav_Season_List.removeAt(position)
                //notify adapter about removed item
//                season_list_adapter!!.notifyDataSetChanged()
                custom_season_list_adapter!!.notifyDataSetChanged()
                return true
                }
        }
    }

    fun season_List_With_Custom_Adapter(){
        //set up array adapter for the list
        custom_season_list_adapter =  myCustomAdapter(this,
                                                        myFav_Season_List,
                                                        myFavSeasonImage)
        //attach custom adapter to the list
        displaySeasonList.adapter = custom_season_list_adapter

        //use anonymous class of list to select the item from list and move to another activity
        displaySeasonList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item_name = parent?.getItemAtPosition(position).toString()
                val details_intent = Intent(this@MainActivity, DetailsActivity::class.java)
                details_intent.putExtra("season_item_name", item_name)
                startActivity(details_intent)
            }
        }
    }
}
