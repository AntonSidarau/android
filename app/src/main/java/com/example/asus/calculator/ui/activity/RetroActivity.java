package com.example.asus.calculator.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.Model;
import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;
import com.example.asus.calculator.tools.adapter.delegate.UserAdapterDelegate;
import com.example.asus.calculator.tools.retrofit.service.UserService;
import com.example.asus.calculator.util.MagicConstants;
import com.example.asus.calculator.util.ModelUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroActivity extends AppCompatActivity {
    private static final String TAG = RetroActivity.class.getSimpleName();

    @BindView(R.id.et_user_name) EditText etUser;
    @BindView(R.id.btn_get_user_by_id) Button btnFetchUser;
    @BindView(R.id.rv_retro) RecyclerView recyclerView;

    private ProductModelRecycleAdapter adapter;
    private List<Model> list;
    private Retrofit retrofit;
    private UserService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        adapter = new ProductModelRecycleAdapter(list);
        adapter.addDelegates(new UserAdapterDelegate());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(MagicConstants.DEFAULT_HTTP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(UserService.class);

        Log.d(TAG, "onCreate: fetching all users");
        service.getAllUsers()
                .subscribeOn(Schedulers.io())
                .map(userModels -> {
                    adapter.addAll(ModelUtil.convertList(userModels));
                    return 0;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModels -> adapter.notifyDataSetChanged());
    }

    @OnClick(R.id.btn_get_user_by_id)
    public void fetch() {
        String text = etUser.getText().toString();
        if (text.length() == 0) {
            return;
        }

        Log.d(TAG, "fetch: before executing query");
        service.getUser(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> Toast.makeText(getApplicationContext(), userModel.toString(),
                        Toast.LENGTH_SHORT).show());

    }


}
