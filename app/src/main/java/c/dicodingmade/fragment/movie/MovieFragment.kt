package c.dicodingmade.fragment.movie


import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import c.dicodingmade.R
import c.dicodingmade.activity.detail.DetailActivity
import c.dicodingmade.model.MovieTvShowData
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {
    private var listMovieTvShows: MutableList<MovieTvShowData> = mutableListOf()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var moviePosterData: TypedArray
    private var movieTitleData: Array<String> = arrayOf()
    private var movieReleaseDateData: Array<String> = arrayOf()
    private var movieDescriptionData: Array<String> = arrayOf()
    private var movies: ArrayList<MovieTvShowData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieRecyclerView()
        dataPreparation()
        addDataMovie()
    }

    private fun movieRecyclerView() {
        movieAdapter = MovieAdapter(listMovieTvShows) {
            val singleMovieData = MovieTvShowData().apply {
                movieTvShowPoster = it.movieTvShowPoster
                movieTvShowTitle = it.movieTvShowTitle
                movieTvShowReleaseDate = it.movieTvShowReleaseDate
                movieTvShowDescription = it.movieTvShowDescription
            }
            val detailMovieWithObjectIntent = Intent(context, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_DATA, singleMovieData)
            startActivity(detailMovieWithObjectIntent)
        }
        rv_movie.adapter = movieAdapter
        rv_movie.isNestedScrollingEnabled = false
    }

    private fun addDataMovie() {
        for (i in 0 until movieTitleData.size) {
            val movieData = MovieTvShowData()
            movieData.movieTvShowPoster = moviePosterData.getResourceId(i, -i)
            movieData.movieTvShowTitle = movieTitleData[i]
            movieData.movieTvShowReleaseDate = movieReleaseDateData[i]
            movieData.movieTvShowDescription = movieDescriptionData[i]
            movies.add(movieData)
        }

        listMovieTvShows.clear()
        listMovieTvShows.addAll(movies)
        movieAdapter.notifyDataSetChanged()
    }

    private fun dataPreparation() {
        moviePosterData = resources.obtainTypedArray(R.array.poster_movie_data)
        movieTitleData = resources.getStringArray(R.array.title_movie_data)
        movieReleaseDateData = resources.getStringArray(R.array.movie_release_date_data)
        movieDescriptionData = resources.getStringArray(R.array.movie_description_data)
    }
}
