package com.example.asus.calculator.dao.impl;

import android.content.Context;

import com.example.asus.calculator.dao.ProductDao;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.util.PreferenceUtil;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;


public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {
    public ProductDaoImpl(Class<Product> dataClass) throws SQLException {
        super(dataClass);
    }

    public ProductDaoImpl(ConnectionSource connectionSource, Class<Product> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ProductDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Product> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Product> getLimitedWithNameAndCalories(long limit, long offset, String category_id,
                                                       String productName, Context context) throws SQLException {
        ProductDao dao = DBHelperFactory.getHelper().getProductDao();
        QueryBuilder<Product, Long> queryBuilder = dao.queryBuilder();

        Where<Product, Long> where = queryBuilder.limit(limit).offset(offset).where().eq(Product.CATEGORY_ID, category_id).and()
                .like(Product.NAME, String.format("%%%s%%", productName));

        PreferenceUtil.addConditions(context, where, Product.CALORIES);
        return dao.query(queryBuilder.prepare());
    }
}
