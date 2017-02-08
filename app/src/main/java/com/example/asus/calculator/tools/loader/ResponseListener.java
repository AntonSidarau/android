package com.example.asus.calculator.tools.loader;

import java.util.List;

public interface ResponseListener<T> {
    void onResponse(List<T> list);
}
