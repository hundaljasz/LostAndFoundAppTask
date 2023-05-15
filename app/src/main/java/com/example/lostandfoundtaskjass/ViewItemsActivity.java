package com.example.lostandfoundtaskjass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {
    RecyclerView showItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        showItems = findViewById(R.id.LFitems);
        DB db = new DB(this);
        List<Items> itemList = db.getItems();
        itemAdaptor showItem = new itemAdaptor(itemList,ViewItemsActivity.this);
        RecyclerView.LayoutManager vertical =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        showItems.setAdapter(showItem);
        showItems.setLayoutManager(vertical);
    }
}