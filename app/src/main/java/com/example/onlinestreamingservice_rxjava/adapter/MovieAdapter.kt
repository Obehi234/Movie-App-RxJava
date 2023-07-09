package com.example.onlinestreamingservice_rxjava.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinestreamingservice_rxjava.R

class MovieAdapter(private val movieList: List<com.example.onlinestreamingservice_rxjava.model.Result>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvMovieName: TextView = itemView.findViewById(R.id.tvMovieName)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val ivMovie: ImageView = itemView.findViewById(R.id.imgMovie)
        val tvLanguage: TextView = itemView.findViewById(R.id.tvLanguage)
        val tvReleaseDate: TextView = itemView.findViewById(R.id.tvDateReleased)

        fun bind(movie: com.example.onlinestreamingservice_rxjava.model.Result) {

            val context = itemView.context
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.error_image)
                .into(ivMovie)


            tvMovieName.text = movie.title
            tvRating.text = movie.vote_average.toString()
            tvLanguage.text = movie.original_language
            tvReleaseDate.text = movie.release_date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false)
        return MovieViewHolder(inflater)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }
}