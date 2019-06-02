package com.kotlin.movies.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.movies.MoviesRepository
import com.kotlin.movies.R
import android.support.v7.widget.Toolbar
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import com.kotlin.movies.*
import kotlinx.android.synthetic.main.activity_movie.*


class MovieActivity: AppCompatActivity() {

    private var movieId: Int = 0

    private var moviesRepository: MoviesRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movieId = intent.getIntExtra("movieId", movieId)

        moviesRepository = MoviesRepository.getInstance()

        moviesRepository!!.getMovie(movieId, object : OnGetMovieCallback {
            override fun onSuccess(movie: Movie) {
                val toolbar: Toolbar = findViewById(R.id.action_bar)
                toolbar.title = movie.title
                if (movie.overview == "")
                    txtViewDesc.text = "Descrição não encontrada"
                else
                    txtViewDesc.text = movie.overview
                if (!isFinishing) {
                    Glide.with(this@MovieActivity)
                        .load("http://image.tmdb.org/t/p/w780" + movie.posterpath)
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                        .into(imgViewMovie)
                }
            }

            override fun onError() {
                finish()
            }
        })
    }

}