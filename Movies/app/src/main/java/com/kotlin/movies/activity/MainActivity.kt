package com.kotlin.movies.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kotlin.movies.MoviesRepository
import com.kotlin.movies.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadSpinner.visibility = View.VISIBLE

        val moviesRepository = MoviesRepository.getInstance()

        moviesRepository.getMovies(object : OnGetMoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                val adapter = MoviesAdapter(movies, callback)
                recyclerView.adapter = adapter
                loadSpinner.visibility = View.GONE
            }

            override fun onError() {
                Toast.makeText(this@MainActivity, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    val callback: OnMoviesClickCallback = object : OnMoviesClickCallback {
        override fun onClick(movie: Movie) {
            val intent = Intent(this@MainActivity, MovieActivity::class.java)
            intent.putExtra("movieId", movie.id)
            startActivity(intent)
        }
    }
}
