package com.example.apiapplication.rest;


import com.example.apiapplication.model.Charact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HarryPotterApiService {
    @GET("characters")
    Call<List<Charact>> getCharacters();
    @GET("character/{id}")
    Call<List<Charact>> getCharactById(@Path("id") String id);
}

