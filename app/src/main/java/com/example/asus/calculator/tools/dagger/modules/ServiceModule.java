package com.example.asus.calculator.tools.dagger.modules;

import com.example.asus.calculator.tools.dagger.annotation.RetroUserScope;
import com.example.asus.calculator.tools.retrofit.service.UserService;
import com.example.asus.calculator.util.MagicConstants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {
    @Provides
    @RetroUserScope
    public UserService provideUserService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MagicConstants.DEFAULT_HTTP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(UserService.class);
    }
}
