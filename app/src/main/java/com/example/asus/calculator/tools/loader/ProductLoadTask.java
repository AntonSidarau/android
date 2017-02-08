package com.example.asus.calculator.tools.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.asus.calculator.dao.ProductDao;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.util.ModelUtil;

import java.sql.SQLException;
import java.util.List;

public class ProductLoadTask extends AsyncTask<String, Void, List<ProductModel>> {
    private static final String LOG_TAG = ProductLoadTask.class.getSimpleName();
    public static final long COUNT = 10;

    private long startIndex;
    private ResponseListener<ProductModel> listener;
    private Context context;

    /**
     * constructor for {@link ProductLoadTask}
     *
     * @param startIndex - loading items, starting from specified index
     * @param listener   - class, that handles returning {@link List} from AsyncTask
     */
    public ProductLoadTask(long startIndex, ResponseListener<ProductModel> listener, Context context) {
        this.startIndex = startIndex;
        this.listener = listener;
        this.context = context;
    }

    /**
     * MUST receive two string parameters: name of product and id of category,
     * that need to search
     *
     * @param params [0] - productName, [1] - category_id
     * @return list with result
     */
    @Override
    protected List<ProductModel> doInBackground(String... params) {
        Log.i(LOG_TAG, "Loading products...");
        String productName = params[0];
        String category_id = params[1];

        List<ProductModel> listModel = null;
        try {
            ProductDao dao = DBHelperFactory.getHelper().getProductDao();
            listModel = ModelUtil.copyList(dao.getLimitedWithNameAndCalories(COUNT, startIndex,
                    category_id, productName, context));
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Loading products due calling onScroll");
        }

        return listModel;
    }

    @Override
    protected void onPostExecute(List<ProductModel> products) {
        listener.onResponse(products);
    }
}
