package com.example.asus.calculator.tools.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asus.calculator.R;
import com.example.asus.calculator.dao.CategoryDao;
import com.example.asus.calculator.dao.ProductDao;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.model.persistent.Dish;
import com.example.asus.calculator.model.persistent.Part;
import com.example.asus.calculator.model.persistent.Product;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "calculator.db";
    private static final int INIT_VERSION = 27;

    private CategoryDao categoryDao;
    private Dao<Dish, Long> dishDao;
    private ProductDao productDao;
    private Dao<Part, Long> partDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, INIT_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Dish.class);
            TableUtils.createTable(connectionSource, Product.class);
            TableUtils.createTable(connectionSource, Part.class);
            Log.i(LOG_TAG, "database created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.i(LOG_TAG, "enter in onUpgrade()");
        if (newVersion > oldVersion) {
            try {
                Log.i(LOG_TAG, "Databases upgraded");
                TableUtils.dropTable(connectionSource, Part.class, true);
                TableUtils.dropTable(connectionSource, Dish.class, true);
                TableUtils.dropTable(connectionSource, Product.class, true);
                TableUtils.dropTable(connectionSource, Category.class, true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public CategoryDao getCategoryDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    public Dao<Dish, Long> getDishDao() throws SQLException {
        if (dishDao == null) {
            dishDao = getDao(Dish.class);
        }
        return dishDao;
    }

    public ProductDao getProductDao() throws SQLException {
        if (productDao == null) {
            productDao = getDao(Product.class);
        }
        return productDao;
    }

    public Dao<Part, Long> getPartDao() throws SQLException {
        if (partDao == null) {
            partDao = getDao(Part.class);
        }
        return partDao;
    }

    public void checkDatabaseVersion() {
        SQLiteDatabase db = this.getWritableDatabase();
    }

}
