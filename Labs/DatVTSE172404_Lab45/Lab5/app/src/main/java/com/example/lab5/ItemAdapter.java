package com.example.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> itemList;
    private Context context;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(item.getContent());

        // Xử lý sự kiện Edit
        holder.btnEdit.setOnClickListener(v -> {
            showEditDialog(item, position);
        });

        // Xử lý sự kiện Delete
        holder.btnDelete.setOnClickListener(v -> {
            itemList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void showEditDialog(Item item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialogView);

        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etContent = dialogView.findViewById(R.id.etContent);
        etTitle.setText(item.getTitle());
        etContent.setText(item.getContent());

        builder.setPositiveButton("Save", (dialog, which) -> {
            item.setTitle(etTitle.getText().toString());
            item.setContent(etContent.getText().toString());
            notifyItemChanged(position);
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}
