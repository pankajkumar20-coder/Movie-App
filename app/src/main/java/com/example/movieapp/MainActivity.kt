package com.example.movieapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: MovieListAdapter
    private lateinit var movieList: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val search = findViewById<android.widget.SearchView>(R.id.searchView)
        search.clearFocus()
        fetchData()
        mAdapter = MovieListAdapter()
        recyclerView.adapter = mAdapter


        search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<Movie> = ArrayList()
        for (item in movieList) {
            if (item.title.lowercase().contains(text.lowercase())) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            mAdapter.filterList(filteredlist)
        }
    }

    private fun fetchData() {
        val url = "https://api.themoviedb.org/3/trending/all/day?api_key=20e8c3d2873b06385e69acbbc1ea5f36"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
            val movieJsonArray = it.getJSONArray("results")
            val movieArray = ArrayList<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJsonObject = movieJsonArray.getJSONObject(i)
                val movie = Movie(
                    movieJsonObject.optString("title"),
                    movieJsonObject.getString("poster_path")
                )
                movieArray.add(movie)
            }
            mAdapter.updateMovie(movieArray)
        },
            {
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}