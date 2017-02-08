package com.example.asus.calculator.tools.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.j256.ormlite.android.AndroidCompiledStatement;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.SQLException;

public class SuggestionProductLoader extends AbstractCursorLoader {
    private static final String LOG_TAG = SuggestionProductLoader.class.getSimpleName();
    private static final Long LIMIT = 4L;

    private String productName;
    private String category_id;

    public SuggestionProductLoader(Context context) {
        super(context);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = null;
        PreparedQuery<Product> query = null;
        Dao<Product, Long> dao = null;

        if (getProductName() == null) {
            Log.i(LOG_TAG, "name of the product is NULL");
            return null;
        }
        try {
            dao = DBHelperFactory.getHelper().getProductDao();
            QueryBuilder<Product, Long> queryBuilder = dao.queryBuilder();
            queryBuilder.limit(LIMIT).where().eq(Product.CATEGORY_ID, getCategory_id()).and()
                    .like(Product.NAME, String.format("%s%%", getProductName()));
            query = queryBuilder.prepare();

            DatabaseConnection connection = dao.getConnectionSource().getReadOnlyConnection(dao.getTableName());
            AndroidCompiledStatement statement = (AndroidCompiledStatement) query.
                    compile(connection, StatementBuilder.StatementType.SELECT);

            cursor = statement.getCursor();
        } catch (SQLException e) {
            Log.e(LOG_TAG, "building statement", e);
        }

        return cursor;
    }
}
