package com.example.asus.calculator.tools.adapter.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.Model;
import com.example.asus.calculator.model.UserModel;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapterDelegate extends AdapterDelegate<List<Model>> {
    private static final String TAG = UserAdapterDelegate.class.getSimpleName();

    @Override
    protected boolean isForViewType(@NonNull List<Model> items, int position) {
        return items.get(position) instanceof UserModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new UserViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Model> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        UserViewHolder vh = (UserViewHolder) holder;
        UserModel model = (UserModel) items.get(position);
        vh.tvName.setText(model.getName());
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_user_name) TextView tvName;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
