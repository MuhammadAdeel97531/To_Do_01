package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewItem extends AppCompatActivity {
    EditText itemName;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        itemName = findViewById(R.id.item_name_inp);
        saveBtn = findViewById(R.id.save_item_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemName.getText().toString().length() != 0)
                {
                    getIntent().putExtra("itemName", itemName.getText().toString());
                    setResult(1, getIntent());
                    finish();
                }
                else
                {
                    itemName.setError("Enter Item Name.");
                }
            }
        });
    }



}