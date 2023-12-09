package edu.co.eci.moviefinder.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import edu.co.eci.moviefinder.databinding.ActivityMainBinding;
import edu.co.eci.moviefinder.model.Movie;
import edu.co.eci.moviefinder.model.MoviesResponse;
import edu.co.eci.moviefinder.repository.OmdbApi;
import edu.co.eci.moviefinder.repository.RetrofitClient;
import edu.co.eci.moviefinder.ui.adapter.MoviesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchButton.setOnClickListener(v -> {
            String searchQuery = binding.searchQueryText.getText().toString();
            searchMovie(searchQuery);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new MoviesAdapter(new ArrayList<>()));
    }

    private void searchMovie(String searchQuery) {
        OmdbApi service = RetrofitClient.getRetrofitInstance().create(OmdbApi.class);
        Call<MoviesResponse> call = service.getMovies(searchQuery, "a2935151");

        Log.d("MainActivity",  searchQuery);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse movieDto = response.body();
                    List<Movie> movies = response.body().getSearch();

                    if (movies != null && movieDto.getResponse().equals("True")) {
                        MoviesAdapter adapter = new MoviesAdapter(movies);
                        binding.recyclerView.setAdapter(adapter);
                        Log.d("MainActivity", "Movie: " + movieDto);
                    }

                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage(), t);
            }
        });

    }
}