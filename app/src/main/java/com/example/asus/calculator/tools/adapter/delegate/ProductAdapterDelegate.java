package com.example.asus.calculator.tools.adapter.delegate;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

public class ProductAdapterDelegate extends AdapterDelegate<List<ProductModel>> {
    private LayoutInflater inflater;
    private Context context;

    public ProductAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
        context = activity.getApplicationContext();
    }

    @Override
    protected boolean isForViewType(@NonNull List<ProductModel> items, int position) {
        return items.get(position) instanceof ProductModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        return new ProductViewHolder(inflater.inflate(R.layout.item_product, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<ProductModel> list, int position,
                                    @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final ProductModel model = list.get(position);
        final ProductViewHolder vh = (ProductViewHolder) holder;
        vh.tvName.setText(model.getName());
        String text = String.format("%s %s", model.getCalories(),
                context.getResources().getString(R.string.textView_secondary_list_product));
        vh.tvCalorie.setText(text);
        vh.checkBox.setChecked(model.isChecked());

        vh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.setChecked(isChecked);
                vh.checkBox.setChecked(isChecked);
            }
        });
    }

    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCalorie;
        CheckBox checkBox;

        ProductViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvCalorie = (TextView) itemView.findViewById(R.id.tv_calorie);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_product_odd);
        }
    }
}
