package com.example.asus.calculator.tools.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Product;

public class SuggestionsProductAdapter extends CursorAdapter {

    public SuggestionsProductAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public SuggestionsProductAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_product_suggestion, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.textView = (TextView) view.findViewById(R.id.tv_product_suggest_name);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String name = cursor.getString(cursor.getColumnIndex(Product.NAME));
        holder.textView.setText(name);
    }

    private static class ViewHolder {
        TextView textView;
    }

}
