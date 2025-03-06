package com.example.lab3_3;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PlayerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Player> playerList;

    public PlayerAdapter (Context context, int layout, List<Player> playerList) {
        this.context = context;
        this.layout = layout;
        this.playerList = playerList;
    }

    @Override
    public int getCount() {
        return playerList.size();
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
        //return null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.layout, null);

        TextView txtName = (TextView) view.findViewById(R.id.textviewName);
        TextView txtTeam = (TextView) view.findViewById(R.id.textviewTeam);
        TextView txtNationality = (TextView) view.findViewById(R.id.textViewNationality);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.imgvCountry);

        Player player = playerList.get(i);
        txtName.setText(player.getName());
        txtTeam.setText(player.getTeam());
        txtNationality.setText(player.getNationality());

        String imageUri = player.getImageUri();
        if (imageUri.startsWith("content://")) {
            imgHinh.setImageURI(Uri.parse(imageUri));
        } else {
            int resId = Integer.parseInt(imageUri);
            imgHinh.setImageResource(resId);
        }

        return view;
    }
}
