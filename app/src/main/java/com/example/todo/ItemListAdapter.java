package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemListAdapter  extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    ItemCollection collection;
    Boolean isReadMode;

    public ItemListAdapter(ItemCollection collection) {
        isReadMode = false;
        this.collection = collection;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        Item item = collection.getItem(position);
        holder.status = collection.getItem(position).getStatus();
        if(holder.status)
        {
            holder.itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.cmpCheck.setChecked(true);
        }
        holder.itemName.setText(item.getName());

        holder.cmpCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                if(holder.cmpCheck.isChecked())
                {
                    collection.getItem(position).setStatus(true);
                    holder.itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else
                {
                    collection.getItem(position).setStatus(false);
                    holder.itemName.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
                }
            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection.getCollection().remove(position);
                notifyDataSetChanged();
            }
        });

        if(isReadMode)
        {
            holder.cmpCheck.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return collection.getCollection().size();
    }

    public void setCollection(ItemCollection collection) {
        this.collection = collection;
        notifyDataSetChanged();
    }

    protected class ItemListViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView removeBtn;
        CheckBox cmpCheck;
        Boolean status;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            status = false;
            itemName = itemView.findViewById(R.id.item_name_tv);
            removeBtn = itemView.findViewById(R.id.remove_item_btn);
            cmpCheck = itemView.findViewById(R.id.cmp_check);
        }
    }

    public void setReadMode(Boolean readMode) {
        isReadMode = readMode;
    }
}
