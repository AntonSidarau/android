package com.example.asus.calculator.tools.dagger;

import com.example.asus.calculator.tools.dagger.annotation.RecycleViewScope;
import com.example.asus.calculator.tools.dagger.modules.AdapterModule;
import com.example.asus.calculator.tools.dagger.modules.ServiceModule;
import com.example.asus.calculator.ui.activity.RecycleActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {AdapterModule.class})
@RecycleViewScope
public interface RecycleViewComponent {
    void inject(RecycleActivity activity);

    RetroUsersComponent addRetroUserComponent(ServiceModule module);
}
