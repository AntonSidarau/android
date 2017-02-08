package com.example.asus.calculator.dao;

import android.content.Context;

import com.example.asus.calculator.model.persistent.Product;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends Dao<Product, Long> {
    // TODO: 10/20/2016 add specific queries
    List<Product> getLimitedWithNameAndCalories(long limit, long offset, String category_id,
                                                String productName, Context context) throws SQLException;
}

