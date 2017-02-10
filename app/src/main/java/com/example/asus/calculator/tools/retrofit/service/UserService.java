package com.example.asus.calculator.tools.retrofit.service;

import com.example.asus.calculator.model.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("user/{name}")
    Call<UserModel> getUser(@Path("name") String name);
}
