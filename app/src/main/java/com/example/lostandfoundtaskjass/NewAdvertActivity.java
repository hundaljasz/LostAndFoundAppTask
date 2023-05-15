package com.example.lostandfoundtaskjass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;

public class NewAdvertActivity extends AppCompatActivity {
    RadioButton radioButton;

    EditText name,phone,desc,date,location;
    Button SaveItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);
        RadioGroup radioGroup = findViewById(R.id.postTypeRadioGroup);
        name = findViewById(R.id.NameItem);
        phone = findViewById(R.id.PhoneItem);
        desc = findViewById(R.id.DescItem);
        date = findViewById(R.id.DateItem);
        location = findViewById(R.id.LocationItem);
        SaveItem = findViewById(R.id.SaveItem);
        DB db = new DB(this);

        SaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if(TextUtils.isEmpty(radioButton.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Select Post Type!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Name!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Phone!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(desc.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Description!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(date.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Date!..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(location.getText().toString().trim())) {
                    Toast.makeText(NewAdvertActivity.this, "Please Enter Location!..", Toast.LENGTH_SHORT).show();
                    return;
                }
                Items i = new Items(
                        name.getText().toString(),
                        phone.getText().toString(),
                        desc.getText().toString(),
                        date.getText().toString(),
                        location.getText().toString(),
                        radioButton.getText().toString().trim().equals("Lost") ? 1 : 0
                );
                db.insertItem(i);
                Toast.makeText(NewAdvertActivity.this, "Advertisement Added Successfully!...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}