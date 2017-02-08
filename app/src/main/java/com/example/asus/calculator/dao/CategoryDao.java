package com.example.asus.calculator.dao;

import com.example.asus.calculator.model.persistent.Category;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao extends Dao<Category, Long> {
    List<Category> getLimitedWithOffset(long limit, long offset) throws SQLException;
}
