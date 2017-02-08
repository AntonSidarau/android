package com.example.asus.calculator.dao.impl;

import com.example.asus.calculator.dao.CategoryDao;
import com.example.asus.calculator.model.persistent.Category;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl extends BaseDaoImpl<Category, Long> implements CategoryDao {
    public CategoryDaoImpl(Class<Category> dataClass) throws SQLException {
        super(dataClass);
    }

    public CategoryDaoImpl(ConnectionSource connectionSource, Class<Category> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CategoryDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Category> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Category> getLimitedWithOffset(long limit, long offset) throws SQLException {
        QueryBuilder<Category, Long> queryBuilder = queryBuilder();
        queryBuilder.limit(limit).offset(offset);
        return query(queryBuilder.prepare());
    }
}
