package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DrinkAdapter extends BaseAdapter {
    private Context context;
    private List<DrinkItem> drinkList;

    public DrinkAdapter(Context context, List<DrinkItem> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int i) {
        return drinkList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        ImageView ivDrink;
        TextView tvDrinkName;
        TextView tvDrinkDescription;
        TextView tvDrinkPrice;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_drink, parent, false);
            holder = new ViewHolder();
            holder.ivDrink = convertView.findViewById(R.id.ivDrink);
            holder.tvDrinkName = convertView.findViewById(R.id.tvDrinkName);
            holder.tvDrinkDescription = convertView.findViewById(R.id.tvDrinkDescription);
            holder.tvDrinkPrice = convertView.findViewById(R.id.tvDrinkPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DrinkItem item = drinkList.get(i);
        holder.ivDrink.setImageResource(item.getImageResId());
        holder.tvDrinkName.setText(item.getName());
        holder.tvDrinkDescription.setText(item.getDescription());
        holder.tvDrinkPrice.setText(String.valueOf(item.getPrice()));

        return convertView;
    }
}
