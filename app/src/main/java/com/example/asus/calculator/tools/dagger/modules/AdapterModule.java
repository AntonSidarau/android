package com.example.asus.calculator.tools.dagger.modules;

import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;
import com.example.asus.calculator.tools.dagger.annotation.RecycleViewScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    @RecycleViewScope
    public ProductModelRecycleAdapter provideAdapter() {
        return new ProductModelRecycleAdapter();
    }
}
