package com.example.asus.calculator.tools.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.ui.view.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CategoryAdapter extends ArrayAdapter<Category> {
    private LayoutInflater inflater;
    private Context context;

    public CategoryAdapter(Context context, List<Category> list) {
        super(context, 0, list);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_category, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_item_category);
            holder.imageView = (SquareImageView) convertView.findViewById(R.id.iv_item_category);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(getItem(position).getName());
        Picasso.with(context).load(R.drawable.cherry).fit().centerCrop()//.transform(new CircleTransformation())
                .into(holder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        SquareImageView imageView;
    }

}
