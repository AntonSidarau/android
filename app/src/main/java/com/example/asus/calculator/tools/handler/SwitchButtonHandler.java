package com.example.asus.calculator.tools.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.asus.calculator.R;
import com.example.asus.calculator.util.MagicConstants;
import com.example.asus.calculator.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class SwitchButtonHandler {
    private static final String LOG_TAG = SwitchButtonHandler.class.getSimpleName();
    private static final int DEFAULT_CAPACITY = 4;

    private SharedPreferences preferences;
    private List<SwitchCompat> switchCompats;
    private String[] valuesHigh;
    private String[] valuesLow;

    public SwitchButtonHandler(Context context) {
        preferences = context.getSharedPreferences(MagicConstants.CALORIFIC_PREFERENCE,
                Context.MODE_PRIVATE);
        switchCompats = new ArrayList<>(DEFAULT_CAPACITY);
        valuesHigh = context.getResources().getStringArray(R.array.calorific_values_high);
        valuesLow = context.getResources().getStringArray(R.array.calorific_values_low);
    }

    public void addSwitchCompat(SwitchCompat switchCompat) {
        switchCompats.add(switchCompat);
    }

    public void listenCalorificSwitches() {
        for (int i = 0; i < switchCompats.size(); i++) {
            switchCompats.get(i).setOnCheckedChangeListener(new Listener(i));
        }
    }


    private class Listener implements CompoundButton.OnCheckedChangeListener {
        private int i;

        Listener(int i) {
            this.i = i;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.i(LOG_TAG, "SwitchCompat #" + (i + 1));
            if (isChecked) {
                for (int j = 0; j < switchCompats.size(); j++) {
                    if (j != i) {
                        switchCompats.get(j).setChecked(false);
                    }
                }

                PreferenceUtil.fill(preferences, valuesHigh[i], valuesLow[i]);
            } else {
                PreferenceUtil.refresh(preferences);
            }
        }
    }
}
