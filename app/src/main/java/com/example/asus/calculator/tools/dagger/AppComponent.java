package com.example.asus.calculator.tools.dagger;

import com.example.asus.calculator.tools.dagger.modules.AdapterModule;

import javax.inject.Singleton;

import dagger.Component;

@Component
@Singleton
public interface AppComponent {
    RecycleViewComponent addRecycleViewComponent(AdapterModule module);
}
