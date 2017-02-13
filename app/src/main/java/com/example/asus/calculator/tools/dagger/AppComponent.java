package com.example.asus.calculator.tools.dagger;

import com.example.asus.calculator.tools.dagger.modules.AdapterModule;
import com.example.asus.calculator.tools.dagger.modules.ServiceModule;
import com.example.asus.calculator.ui.activity.RecycleActivity;
import com.example.asus.calculator.ui.activity.RetroActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AdapterModule.class, ServiceModule.class})
@Singleton
public interface AppComponent {
    void inject(RecycleActivity activity);

    void inject(RetroActivity activity);
}
