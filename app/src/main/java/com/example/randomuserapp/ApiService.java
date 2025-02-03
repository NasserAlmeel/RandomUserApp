package com.example.randomuserapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/?results=10") // Fetch 10 random users
    Call<ApiResponse> getRandomUsers();
}
