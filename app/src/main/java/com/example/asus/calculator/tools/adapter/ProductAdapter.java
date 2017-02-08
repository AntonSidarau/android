package com.example.asus.calculator.tools.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends ArrayAdapter<ProductModel> {
    private LayoutInflater inflater;
    private List<ProductModel> checkedList;

    public ProductAdapter(Context context, List<ProductModel> list) {
        super(context, 0, list);
        checkedList = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tvCalorie = (TextView) convertView.findViewById(R.id.tv_calorie);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_product_odd);
            convertView.setTag(holder);
            holder.checkBox.setOnCheckedChangeListener(listener);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductModel model = getItem(position);
        if (model != null) {
            holder.tvName.setText(model.getName());
            String text = model.getCalories() + " " +
                    getContext().getResources().getString(R.string.textView_secondary_list_product);
            holder.tvCalorie.setText(text);
            holder.checkBox.setTag(model);
            holder.checkBox.setChecked(model.isChecked());
        }

        return convertView;
    }

    public List<ProductModel> getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(List<ProductModel> checkedList) {
        this.checkedList = checkedList;
    }

    @Override
    public void clear() {
        super.clear();
        checkedList.clear();
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvCalorie;
        CheckBox checkBox;
    }

    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ProductModel model = (ProductModel) buttonView.getTag();
            model.setChecked(isChecked);

            if (isChecked) {
                checkedList.add(new ProductModel(model.getProduct()));
            } else {
                checkedList.remove(model);
            }
        }
    };
}
