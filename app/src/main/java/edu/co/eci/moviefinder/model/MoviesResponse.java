package edu.co.eci.moviefinder.model;


import androidx.annotation.NonNull;

import java.util.List;

public class MoviesResponse {
    private List<Movie> Search;
    private String totalResults;
    private String Response;

    public List<Movie> getSearch() {
        return Search;
    }

    public void setSearch(List<Movie> search) {
        this.Search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        this.Response = response;
    }

    @NonNull
    @Override
    public String toString() {
        return Response + ", " + Search.toString();
    }
}

