package com.example.asus.calculator.tools.dagger;

import com.example.asus.calculator.tools.dagger.annotation.RetroUserScope;
import com.example.asus.calculator.tools.dagger.modules.ServiceModule;
import com.example.asus.calculator.ui.activity.RetroActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {ServiceModule.class})
@RetroUserScope
public interface RetroUsersComponent {
    void inject(RetroActivity activity);
}
