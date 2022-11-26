package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieListAdapter(): RecyclerView.Adapter<NewsViewHolder>() {

    private var items: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_poster, parent, false)
        val viewHolder = NewsViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        Glide.with(holder.itemView.context).load("https://image.tmdb.org/t/p/w342${currentItem.posterPath}").into(holder.imageView)
    }

    override fun getItemCount(): Int = items.size

    fun filterList(filterlist: ArrayList<Movie>) {
        items = filterlist
        notifyDataSetChanged()
    }

    fun updateMovie(updatedMovie: ArrayList<Movie>) {
        items.clear()
        items.addAll(updatedMovie)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleView = itemView.findViewById<TextView>(R.id.textView)
    val imageView = itemView.findViewById<ImageView>(R.id.movieImage)
}