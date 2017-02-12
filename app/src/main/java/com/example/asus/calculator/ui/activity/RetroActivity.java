package com.example.asus.calculator.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.UserModel;
import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;
import com.example.asus.calculator.tools.retrofit.service.UserService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroActivity extends AppCompatActivity {
    private static final String TAG = RetroActivity.class.getSimpleName();

    @BindView(R.id.et_user_name) EditText etUser;
    @BindView(R.id.btn_get_user_by_id) Button btnFetchUser;
    @BindView(R.id.rv_retro) RecyclerView recyclerView;

    private ProductModelRecycleAdapter adapter;
    private List<ProductModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_user_by_id)
    public void fetch() {
        String text = etUser.getText().toString();
        if (text.length() == 0) {
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.4:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d(TAG, "fetch: before executing query");
        new AsyncRequest().execute(text);
       /* UserService service = retrofit.create(UserService.class);
        service.getUser(text)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn()
*/

    }

    class AsyncRequest extends AsyncTask<String, Void, UserModel> {
        @Override
        protected UserModel doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.110.101:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserService service = retrofit.create(UserService.class);
            UserModel user = null;
            Call<UserModel> call = service.getUser(params[0]);
            try {
                user = call.execute().body();
                Log.d(TAG, user.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return user;
        }

        @Override
        protected void onPostExecute(UserModel userModel) {
            Toast.makeText(RetroActivity.this.getApplicationContext(), userModel.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
