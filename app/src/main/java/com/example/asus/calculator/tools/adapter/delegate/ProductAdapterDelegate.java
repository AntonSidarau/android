package com.example.asus.calculator.tools.adapter.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.Model;
import com.example.asus.calculator.model.ProductModel;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapterDelegate extends AdapterDelegate<List<Model>> implements View.OnLongClickListener {
    private static final String TAG = ProductAdapterDelegate.class.getSimpleName();

    private OnLongClickCheckBoxListener listener;

    public interface OnLongClickCheckBoxListener {
        void update(final boolean newState);
    }

    public ProductAdapterDelegate(OnLongClickCheckBoxListener listener) {
        this.listener = listener;
    }

    @Override
    protected boolean isForViewType(@NonNull List<Model> items, int position) {
        return items.get(position) instanceof ProductModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ProductViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Model> list, int position,
                                    @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ProductViewHolder vh = (ProductViewHolder) holder;
        ProductModel model = (ProductModel) list.get(position);
        vh.tvName.setText(model.getName());
        String text = String.format("%s %s", model.getCalories(), vh.getCalorieText());
        vh.tvCalorie.setText(text);
        vh.checkBox.setTag(model);
        vh.checkBox.setOnCheckedChangeListener(null);
        vh.checkBox.setChecked(model.isChecked());

        vh.checkBox.setOnCheckedChangeListener(vh);
        vh.checkBox.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        boolean newState = !((CheckBox) v).isChecked();
        listener.update(newState);
        return true;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.tv_product_name) TextView tvName;
        @BindView(R.id.tv_calorie) TextView tvCalorie;
        @BindView(R.id.cb_product_odd) CheckBox checkBox;

        @BindString(R.string.textView_secondary_list_product) String calorieText;

        ProductViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ProductModel model = (ProductModel) buttonView.getTag();
            model.setChecked(isChecked);
        }

        String getCalorieText() {
            return calorieText;
        }
    }
}
