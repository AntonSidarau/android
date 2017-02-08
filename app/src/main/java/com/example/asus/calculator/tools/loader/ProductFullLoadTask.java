package com.example.asus.calculator.tools.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.asus.calculator.dao.ProductDao;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.util.ModelUtil;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class ProductFullLoadTask extends AsyncTask<Void, Void, List<ProductModel>> {
    private static final String LOG_TAG = ProductLoadTask.class.getSimpleName();
    private static final long COUNT = 10;

    private long startIndex;
    private Context context;
    private ResponseListener<ProductModel> listener;

    public ProductFullLoadTask(long startIndex, Context context, ResponseListener<ProductModel> listener) {
        this.startIndex = startIndex;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<ProductModel> doInBackground(Void... params) {
        List<ProductModel> list = Collections.emptyList();
        try {
            ProductDao dao = DBHelperFactory.getHelper().getProductDao();
            QueryBuilder<Product, Long> queryBuilder = dao.queryBuilder().limit(COUNT).offset(startIndex);
            list = ModelUtil.copyList(dao.query(queryBuilder.prepare()));
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Loading products due calling onScroll");
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<ProductModel> products) {
        listener.onResponse(products);
    }
}
