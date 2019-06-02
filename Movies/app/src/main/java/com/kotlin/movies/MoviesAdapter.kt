package com.kotlin.movies

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_card_view.view.*
import android.R.attr.onClick



class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private var movies: List<Movie>
    var callback: OnMoviesClickCallback

    constructor(movies: List<Movie>, callback: OnMoviesClickCallback) {
        this.movies = movies
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_card_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder : RecyclerView.ViewHolder {
        var imgView: ImageView
        var textView: TextView

        lateinit var movie: Movie

        constructor(itemView: View) : super (itemView) {
            imgView = itemView.imageView
            textView = itemView.textView
            itemView.setOnClickListener { callback.onClick(movie) }
        }

        fun bind(movie: Movie) {
            textView.text = movie.title
            this.movie = movie
            Glide.with(itemView)
                .load("http://image.tmdb.org/t/p/w500" + movie.posterpath)
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(imgView)
        }
    }

}

interface OnMoviesClickCallback {
    fun onClick(movie: Movie)
}