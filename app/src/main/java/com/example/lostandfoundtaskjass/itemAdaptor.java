package com.example.lostandfoundtaskjass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class itemAdaptor extends RecyclerView.Adapter<itemAdaptor.ItemViewHolder>{
    private List<Items> itemList;
    private Context context;

    public itemAdaptor(List<Items> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
    @NonNull
    @Override
    public itemAdaptor.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_card,parent,false);
        return new ItemViewHolder(itemView,itemList,context);
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdaptor.ItemViewHolder holder, int position) {
        holder.name.setText(itemList.get(position).getName());
        holder.location.setText(itemList.get(position).getLocation());
        holder.date.setText(itemList.get(position).getDate());
        if (itemList.get(position).getPost_type() == 1) {
            holder.postType.setText("Lost");
        } else {
            holder.postType.setText("Found");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        List<Items> item;
        private Context mContext;
        TextView name,location,date,postType;
        public ItemViewHolder(@NonNull View itemView,List<Items> item,Context context) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            postType = itemView.findViewById(R.id.itemType);
            this.mContext = context;
            this.item = item;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            String postType = item.get(position).getPost_type() == 1 ? "Lost" : "Found";
            Intent intent = new Intent(mContext, ItemDetailActivity.class);
            intent.putExtra("id", item.get(position).getId());
            intent.putExtra("name", item.get(position).getName());
            intent.putExtra("post_type", postType);
            intent.putExtra("desc", item.get(position).getDescription());
            intent.putExtra("location",item.get(position).getLocation());
            intent.putExtra("phone", item.get(position).getPhone());
            intent.putExtra("date", item.get(position).getDate());
            mContext.startActivity(intent);
        }
    }
}
