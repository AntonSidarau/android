package com.example.asus.calculator.tools.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductPickedAdapter extends ArrayAdapter<ProductModel> {
    private static final String LOG_TAG = ProductPickedAdapter.class.getSimpleName();
    private static final int MASS_CHANGE_VALUE = 50;

    private LayoutInflater inflater;
    private List<ProductModel> checkedList;

    public ProductPickedAdapter(Context context, List<ProductModel> list) {
        super(context, 0, list);
        checkedList = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product_picked, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tvCalorie = (TextView) convertView.findViewById(R.id.tv_calorie);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_product_odd);
            holder.ibPlus = (ImageButton) convertView.findViewById(R.id.ib_more_mass);
            holder.ibMinus = (ImageButton) convertView.findViewById(R.id.ib_less_mass);
            convertView.setTag(holder);
            holder.checkBox.setOnCheckedChangeListener(listener);
            holder.ibMinus.setOnClickListener(massChangerListener);
            holder.ibPlus.setOnClickListener(massChangerListener);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductModel model = getItem(position);
        if (model != null) {
            holder.tvName.setText(model.getName());
            String text = model.getCalories() + " " +
                    getContext().getResources().getString(R.string.textView_secondary_list_product) + ", " +
                    "mass: " + model.getMass() + " g";
            holder.tvCalorie.setText(text);
            holder.checkBox.setTag(model);
            holder.checkBox.setChecked(model.isChecked());
            holder.ibPlus.setTag(model);
            holder.ibMinus.setTag(model);
        }

        return convertView;
    }

    public List<ProductModel> getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(List<ProductModel> checkedList) {
        this.checkedList = checkedList;
    }

    public void removeChecked() {
        Log.i(LOG_TAG, "items in list: " + getCount());
        for (int i = checkedList.size() - 1; i >= 0; i--) {
            Log.d(LOG_TAG, "item pos: " + getPosition(checkedList.get(i)));
            remove(checkedList.get(i));
            checkedList.remove(i);
        }

        Log.i(LOG_TAG, "items in list after remove: " + getCount());
        Log.i(LOG_TAG, "items in checkedList after remove: " + checkedList.size());

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
        ImageButton ibPlus;
        ImageButton ibMinus;
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

    private View.OnClickListener massChangerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_more_mass:
                    Log.d(LOG_TAG, "+ clicked");
                    ProductModel modelPlus = (ProductModel) v.getTag();
                    modelPlus.setMass(modelPlus.getMass() + MASS_CHANGE_VALUE);
                    notifyDataSetChanged();
                    break;

                case R.id.ib_less_mass:
                    ProductModel modelMinus = (ProductModel) v.getTag();
                    if (modelMinus.getMass() > MASS_CHANGE_VALUE) {
                        modelMinus.setMass(modelMinus.getMass() - MASS_CHANGE_VALUE);
                        notifyDataSetChanged();
                    }
                    Log.d(LOG_TAG, "- clicked");
                    break;
            }
        }
    };
}
