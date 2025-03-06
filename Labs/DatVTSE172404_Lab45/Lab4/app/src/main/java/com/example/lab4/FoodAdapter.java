package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter extends BaseAdapter {

    public FoodAdapter(Context context, List<FoodItem> foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    private Context context;
    private List<FoodItem> foodList;

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    static class ViewHolder {
        ImageView ivFood;
        TextView tvFoodName;
        TextView tvFoodDescription;
        TextView tvFoodPrice;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_food, parent, false);
            holder = new ViewHolder();
            holder.ivFood = convertView.findViewById(R.id.ivFood);
            holder.tvFoodName = convertView.findViewById(R.id.tvFoodName);
            holder.tvFoodDescription = convertView.findViewById(R.id.tvFoodDescription);
            holder.tvFoodPrice = convertView.findViewById(R.id.tvFoodPrice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FoodItem item = foodList.get(i);

        holder.ivFood.setImageResource(item.getImageResId());
        holder.tvFoodName.setText(item.getName());
        holder.tvFoodDescription.setText(item.getDescription());
        holder.tvFoodPrice.setText(String.valueOf(item.getPrice()));

        return convertView;
    }
}
