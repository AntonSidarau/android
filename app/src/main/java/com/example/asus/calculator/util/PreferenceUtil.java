package com.example.asus.calculator.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

import static com.example.asus.calculator.util.MagicConstants.CALORIFIC_PREFERENCE;
import static com.example.asus.calculator.util.MagicConstants.CALORIFIC_PREFERENCE_COND_HIGH;
import static com.example.asus.calculator.util.MagicConstants.CALORIFIC_PREFERENCE_COND_LOW;

public class PreferenceUtil {
    public static final String DEFAULT_VALUE = "-1";

    public static void fill(SharedPreferences pref, String firstValue, String secondValue) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(MagicConstants.CALORIFIC_PREFERENCE_COND_HIGH, firstValue);
        editor.putString(MagicConstants.CALORIFIC_PREFERENCE_COND_LOW, secondValue);
        editor.apply();
    }

    public static void refresh(SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(MagicConstants.CALORIFIC_PREFERENCE_COND_HIGH, DEFAULT_VALUE);
        editor.putString(MagicConstants.CALORIFIC_PREFERENCE_COND_LOW, DEFAULT_VALUE);
        editor.apply();
    }

    public static void refresh(Context context, String preferencesName) {
        SharedPreferences pref = context.getSharedPreferences(preferencesName,
                Context.MODE_PRIVATE);
        refresh(pref);
    }

    // TODO: 11/2/2016 remove
    @Deprecated
    public static String addConditionsTest(Context context) {
        SharedPreferences pref = context.getSharedPreferences(CALORIFIC_PREFERENCE, Context.MODE_PRIVATE);
        String low = pref.getString(CALORIFIC_PREFERENCE_COND_LOW, "-1");
        String high = pref.getString(CALORIFIC_PREFERENCE_COND_HIGH, "-1");
        boolean isHighNotEmpty = false;
        boolean isLowNotEmpty = false;

        if (!low.contentEquals("-1")) {
            isLowNotEmpty = true;
        }

        if (!high.contentEquals("-1")) {
            isHighNotEmpty = true;
        }

        if (isLowNotEmpty && isHighNotEmpty) {
            return "BETWEEN " + low + " AND " + high;
        }

        if (isLowNotEmpty) {
            return "> " + low;
        }

        if (isHighNotEmpty) {
            return "< " + high;
        }

        return "nothing";
    }

    public static Where<?, ?> addConditions(Context context, Where<?, ?> where,
                                            String columnName) throws SQLException {
        SharedPreferences pref = context.getSharedPreferences(CALORIFIC_PREFERENCE, Context.MODE_PRIVATE);
        String low = pref.getString(CALORIFIC_PREFERENCE_COND_LOW, DEFAULT_VALUE);
        String high = pref.getString(CALORIFIC_PREFERENCE_COND_HIGH, DEFAULT_VALUE);
        boolean isHighNotEmpty = false;
        boolean isLowNotEmpty = false;

        if (!low.contentEquals(DEFAULT_VALUE)) {
            isLowNotEmpty = true;
        }

        if (!high.contentEquals(DEFAULT_VALUE)) {
            isHighNotEmpty = true;
        }

        if (isLowNotEmpty && isHighNotEmpty) {
            return where.and().between(columnName, low, high);
        }

        if (isLowNotEmpty) {
            return where.and().ge(columnName, low);
        }

        if (isHighNotEmpty) {
            return where.and().le(columnName, high);
        }

        return where;
    }
}
