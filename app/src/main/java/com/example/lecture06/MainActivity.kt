package com.example.lecture06

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.MatrixCursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.contains
import androidx.cursoradapter.widget.CursorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val myFav_Season_List: MutableList<String> = ArrayList()

    private val myFavSeasonImage: MutableMap<String, Int> = mutableMapOf(
        "Silicon Valley" to R.drawable.siliconvalley,
        "Game of Thrones" to R.drawable.gameofthrones,
        "Big Bang Theory" to R.drawable.bigbangtheory,
        "Prison Break" to R.drawable.prisonbreak,
        "Citizen Khan" to R.drawable.citizenkhan,
        "Divinci Demons" to R.drawable.divincidemons,
        "Mr. Robot" to R.drawable.mrrobot,
        "House of Cards" to R.drawable.houseofcards,
        "Sherlock Holmes" to R.drawable.sherlockholmes,
        "The Witcher" to R.drawable.witcher
    )

    private var season_list_adapter: ArrayAdapter<String>? = null
    private var custom_season_list_adapter: myCustomAdapter? = null

    //user search_array_list to duplicate season names for displaying data on search result
    private var search_array_list: MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //load season names in array
        loadData()

        //use simple list view technique to display or remove list of seasons
        season_List_With_PreDefined_Adapter()

//        //use custom list view technique to display or remove list of seasons
//        season_List_With_Custom_Adapter()
//
//        //remove season item by using Long Click Listener
//        remove_Season_ListItem()


    }

    fun season_List_With_PreDefined_Adapter() {
        season_Array_Adapter()
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


    fun season_Array_Adapter(){
        //set up array adapter for the search list
        season_list_adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            search_array_list
        )

//        //set up array adapter for the predefined list
//        season_list_adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            myFav_Season_List
//        )
    }

    fun remove_Season_ListItem() {
        displaySeasonList.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
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

    fun season_List_With_Custom_Adapter() {
        //set up array adapter for the list
        custom_season_list_adapter = myCustomAdapter(
            this,
            myFav_Season_List,
            myFavSeasonImage
        )
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity2, menu)
        //convert search menu item as search view
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                hideKeyboard()
                if (query!!.isNotEmpty()) {
                    search_array_list.clear()
                    val search = query.toLowerCase()
                    myFav_Season_List.forEach {
                        if (it.toLowerCase().contains(search)) {
                            search_array_list.add(it)
                        }
                    }
                    season_list_adapter!!.notifyDataSetChanged()
                } else {
                    search_array_list.clear()
                    search_array_list.addAll(myFav_Season_List)
                    season_list_adapter!!.notifyDataSetChanged()
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var selectedItem = ""
        when (item?.itemId) {
            R.id.home -> selectedItem = "Home"
            R.id.setting -> {
                selectedItem = "Settings"
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.help -> selectedItem = "Help"
            R.id.exit -> exitProcess(0)
        }
        Toast.makeText(
            this, "You selected $selectedItem",
            Toast.LENGTH_SHORT
        ).show()
        return super.onOptionsItemSelected(item)
    }

    fun loadData() {
        //Add seasons' names into array list
        myFav_Season_List.add("Silicon Valley")
        myFav_Season_List.add("Game of Thrones")
        myFav_Season_List.add("Big Bang Theory")
        myFav_Season_List.add("Prison Break")
        myFav_Season_List.add("Citizen Khan")
        myFav_Season_List.add("Divinci Demons")
        myFav_Season_List.add("Mr. Robot")
        myFav_Season_List.add("House of Cards")
        myFav_Season_List.add("Sherlock Holmes")
        myFav_Season_List.add("The Witcher")
        search_array_list.addAll(myFav_Season_List)
    }

    fun hideKeyboard() {
        // Check if no view has focus:
        val view = this.currentFocus
        view?.let { v ->
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

}
