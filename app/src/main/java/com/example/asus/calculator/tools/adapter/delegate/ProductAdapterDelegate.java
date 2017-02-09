package com.example.asus.calculator.tools.adapter.delegate;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

public class ProductAdapterDelegate extends AdapterDelegate<List<ProductModel>> implements View.OnLongClickListener {
    private static final String TAG = ProductAdapterDelegate.class.getSimpleName();

    private LayoutInflater inflater;
    private Context context;
    private ProductModelRecycleAdapter adapter;

    public ProductAdapterDelegate(Activity activity, ProductModelRecycleAdapter adapter) {
        inflater = activity.getLayoutInflater();
        context = activity.getApplicationContext();
        this.adapter = adapter;
    }

    @Override
    protected boolean isForViewType(@NonNull List<ProductModel> items, int position) {
        return items.get(position) instanceof ProductModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = inflater.inflate(R.layout.item_product, null, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<ProductModel> list, int position,
                                    @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder vh = (ViewHolder) holder;
        ProductModel model = list.get(position);
        vh.tvName.setText(model.getName());
        String text = String.format("%s %s", model.getCalories(),
                context.getResources().getString(R.string.textView_secondary_list_product));
        vh.tvCalorie.setText(text);
        vh.checkBox.setTag(model);
        vh.checkBox.setOnCheckedChangeListener(null);
        vh.checkBox.setChecked(model.isChecked());

        Log.d(TAG, "onBindViewHolder: " + model.getName() + " : " + model.isChecked());
        vh.checkBox.setOnCheckedChangeListener(vh);
        vh.checkBox.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG, "onLongClick: executed");
        boolean newState = !((CheckBox) v).isChecked();
        new AsyncSelector().execute(newState);
        return true;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        TextView tvName;
        TextView tvCalorie;
        CheckBox checkBox;

        ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_product_name);
            tvCalorie = (TextView) view.findViewById(R.id.tv_calorie);
            checkBox = (CheckBox) view.findViewById(R.id.cb_product_odd);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ProductModel model = (ProductModel) buttonView.getTag();
            model.setChecked(isChecked);
            Log.d(TAG, model.getName() + " : " + isChecked);
        }
    }

    private class AsyncSelector extends AsyncTask<Boolean, Void, Void> {
        @Override
        protected Void doInBackground(Boolean... params) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                adapter.getList().get(i).setChecked(params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
        }
    }
}
