package com.example.asus.calculator.tools.retrofit.service;

import com.example.asus.calculator.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("user/{name}")
        // Observable<UserModel> getUser(@Path("name") String name);
    Call<UserModel> getUser(@Path("name") String name);

    @GET("user/all")
    Call<List<UserModel>> getAllUsers();
}
