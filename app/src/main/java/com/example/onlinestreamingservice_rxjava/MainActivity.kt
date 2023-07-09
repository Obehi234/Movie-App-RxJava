package com.example.onlinestreamingservice_rxjava

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinestreamingservice_rxjava.adapter.MovieAdapter
import com.example.onlinestreamingservice_rxjava.api.ApiService
import com.example.onlinestreamingservice_rxjava.api.RetrofitInstance
import com.example.onlinestreamingservice_rxjava.connectivity.ConnectivityLiveData
import com.example.onlinestreamingservice_rxjava.utils.Constants.AuthorizationHeader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: MovieAdapter
    private lateinit var disposable: Disposable
    private lateinit var connectivityLiveData: ConnectivityLiveData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectivityLiveData = ConnectivityLiveData(application)

        apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

        recyclerView = findViewById(R.id.rvMovie)
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar = findViewById(R.id.progBarMovie)

        connectivityLiveData.observe(this) { isConnected ->
            if (isConnected) {
                progressBar.visibility = View.VISIBLE
                getMovie()
            } else {
                Toast.makeText(this, "Error: Please check network connectivity", Toast.LENGTH_LONG).show()
                Log.d("NetworkConnectivity", "Failed: No Internet")
            }
        }


    }
    private fun getMovie() {
        disposable = apiService.getMovies(AuthorizationHeader)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movieResponse ->
                progressBar.visibility = View.GONE
                adapter = MovieAdapter(movieResponse.results ?: listOf())
                recyclerView.adapter = adapter
                Log.d("MovieGenerator", "get Movies: ${movieResponse.results}")

            },
                { error ->
                    progressBar.visibility = View.GONE
                    Log.d("MovieGenerator", "Error: ${error.message}")
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        connectivityLiveData.removeObservers(this)

    }
}