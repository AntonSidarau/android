package com.example.asus.calculator.tools.adapter;

import android.content.Context;
import android.os.AsyncTask;
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

import java.util.List;

public class ProductRecycleAdapter extends RecyclerView.Adapter<ProductRecycleAdapter.ViewHolder>
        implements View.OnLongClickListener {
    private static final String TAG = ProductRecycleAdapter.class.getSimpleName();

    private List<ProductModel> list;
    private Context context;

    public ProductRecycleAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.tvName.setText(model.getName());
        String text = String.format("%s %s", model.getCalories(),
                context.getResources().getString(R.string.textView_secondary_list_product));
        holder.tvCalorie.setText(text);
        holder.checkBox.setTag(model);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(model.isChecked());

        Log.d(TAG, "onBindViewHolder:: " + model.getName() + " : " + model.isChecked());
        holder.checkBox.setOnCheckedChangeListener(holder);
        holder.checkBox.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG, "onLongClick: executed");
        boolean newState = !((CheckBox) v).isChecked();
        /*for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(newState);
        }
        notifyDataSetChanged();*/
        new AsyncSelector(this).execute(newState);
        return true;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ProductModel> newList) {
        list.addAll(newList);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private static final String TAG = ViewHolder.class.getSimpleName();

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

    private static class AsyncSelector extends AsyncTask<Boolean, Void, Void> {
        private ProductRecycleAdapter adapter;

        AsyncSelector(ProductRecycleAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(Boolean... params) {
            for (int i = 0; i < adapter.list.size(); i++) {
                adapter.list.get(i).setChecked(params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
        }
    }
}
