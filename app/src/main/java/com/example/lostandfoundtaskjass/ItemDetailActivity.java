package com.example.lostandfoundtaskjass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetailActivity extends AppCompatActivity {
    TextView name,phone,desc,location,date,post_type;
    int id;
    Button deleteBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent itemReceived = getIntent();

        name = findViewById(R.id.NameofItem);
        phone = findViewById(R.id.Phoneofitem);
        desc  = findViewById(R.id.Descofitem);
        location = findViewById(R.id.Locationofitem);
        date = findViewById(R.id.Dateofitem);
        post_type = findViewById(R.id.postType);
        deleteBTN = findViewById(R.id.btnDeleteitem);
        id = itemReceived.getIntExtra("id",0);
        name.setText(itemReceived.getStringExtra("name"));
        phone.setText(itemReceived.getStringExtra("phone"));
        desc.setText(itemReceived.getStringExtra("desc"));
        location.setText(itemReceived.getStringExtra("location"));
        date.setText(itemReceived.getStringExtra("date"));
        post_type.setText(itemReceived.getStringExtra("post_type"));
        DB db = new DB(this);

        deleteBTN.setOnClickListener(view -> {
            db.deleteItem(id);
            Intent intent = new Intent(ItemDetailActivity.this, ViewItemsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}