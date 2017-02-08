package com.example.asus.calculator.tools.loader;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.asus.calculator.dao.CategoryDao;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.tools.db.DBHelperFactory;

import java.sql.SQLException;
import java.util.List;


public class CategoryLoadTask extends AsyncTask<Void, Void, List<Category>> {
    private static final String LOG_TAG = CategoryLoadTask.class.getSimpleName();
    private static final long COUNT = 6;

    private long startIndex;
    private ResponseListener<Category> listener;
    private ProgressBar progressBar;

    public CategoryLoadTask(long startIndex, ResponseListener<Category> listener) {
        this.startIndex = startIndex;
        this.listener = listener;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected List<Category> doInBackground(Void... params) {
        Log.i(LOG_TAG, "Loading categories...");

        List<Category> list = null;
        try {
            CategoryDao dao = DBHelperFactory.getHelper().getCategoryDao();
            list = dao.getLimitedWithOffset(COUNT, startIndex);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Loading categories due to onScroll");
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        listener.onResponse(categories);
    }
}
