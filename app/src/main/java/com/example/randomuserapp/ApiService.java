package com.example.randomuserapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/")
    Call<ApiResponse> getRandomUsers(@Query("results") int results, @Query("page") int page);
}
