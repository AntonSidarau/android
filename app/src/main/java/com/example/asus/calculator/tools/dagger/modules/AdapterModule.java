package com.example.asus.calculator.tools.dagger.modules;

import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    @Singleton
    public ProductModelRecycleAdapter provideAdapter() {
        return new ProductModelRecycleAdapter();
    }
}
