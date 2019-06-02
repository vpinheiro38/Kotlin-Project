package com.kotlin.movies

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path

class MoviesRepository {

    private val LANGUAGE: String = "pt-BR"

    private val api: TMDbApi

    constructor(api: TMDbApi) {
        this.api = api
    }

    companion object {
        private var repository: MoviesRepository? = null

        fun getInstance(): MoviesRepository {
            if (repository == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                repository = MoviesRepository(retrofit.create<TMDbApi>(TMDbApi::class.java!!))
            }

            return repository!!
        }
    }


    fun getMovies(callback: OnGetMoviesCallback) {
        api.getPopularMovies(BuildConfig.TMDB_API_KEY, LANGUAGE, 1)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful()) {
                        val moviesResponse = response.body()
                        if (moviesResponse != null) {
                            callback.onSuccess(moviesResponse.results)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onError()
                }
            })
    }

    fun getMovie(movieId: Int, callback: OnGetMovieCallback) {
        api.getMovie(movieId, BuildConfig.TMDB_API_KEY, LANGUAGE)
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        if (movie != null) {
                            callback.onSuccess(movie)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    callback.onError()
                }
            })
    }
}

data class MoviesResponse(
    @SerializedName("results")
    @Expose
    val results: List<Movie>,

    @SerializedName("page")
    @Expose
    val page: Int
)

interface OnGetMoviesCallback {
    fun onSuccess(movies: List<Movie>)
    fun onError()
}

interface OnGetMovieCallback {
    fun onSuccess(movie: Movie)
    fun onError()
}

interface TMDbApi {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKEy: String,
        @Query("language") language: String
    ): Call<Movie>
}