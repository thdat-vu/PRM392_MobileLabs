package com.example.lab3_2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TraiCayAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<TraiCay> traiCayList;

    public TraiCayAdapter(Context context, int layout, List<TraiCay> traiCayList){
        this.context = context;
        this.layout = layout;
        this.traiCayList = traiCayList;
    }

    @Override
    public int getCount() {
//        return 0;
        return traiCayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.layout, null);
        TextView txtTen = (TextView) view.findViewById(R.id.textviewTen);
        TextView txtMota = (TextView) view.findViewById(R.id.textviewMota);

        ImageView imgHinh = (ImageView) view.findViewById(R.id.imageviewHinh);

        TraiCay traiCay = traiCayList.get(i);
        txtTen.setText(traiCay.getTen());
        txtMota.setText(traiCay.getMota());
        //imgHinh.setImageResource(traiCay.getHinh());
        String imageUri = traiCay.getHinh();
        if (imageUri.startsWith("content://")) {
            imgHinh.setImageURI(Uri.parse(imageUri));
        } else {
            int resId = Integer.parseInt(imageUri);
            imgHinh.setImageResource(resId);
        }
        return view;
    }


}
