package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TaskListAdapter.Click {
    ImageButton addBtn;
    ImageView editBtn;
    ImageView empImg;
    TextView empTv;
    RecyclerView taskListRv;
    TaskListAdapter listAdapter;
    TaskCollection taskCollection;
    FileStorage storage;
    Boolean editFlag;
    ImageView delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        storage = new FileStorage(getBaseContext());
        taskCollection = new TaskCollection();
        //Load Data from file
        storage.loadCollection(taskCollection);

        editFlag = false;
        editBtn = findViewById(R.id.edit_btn);
        addBtn = findViewById(R.id.add_btn);
        empImg = findViewById(R.id.emp_iv);
        empTv = findViewById(R.id.emp_tv);
        taskListRv = findViewById(R.id.task_list_rv);

        if(taskCollection.getCollection().size() == 0)
        {
            taskListRv.setVisibility(View.INVISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                findViewById(R.id.main_wrapper).setForegroundGravity(Gravity.CENTER);
            }
        }

        ifCollectionEmpty();
        listAdapter = new TaskListAdapter(MainActivity.this,taskCollection);

        taskListRv.setAdapter(listAdapter);
        taskListRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateList.class);
                startActivity(i);
                storage.updateCollection(taskCollection);
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = 0;
                if(editFlag)
                {
                    editFlag = false;
                    visibility = View.GONE;
                }
                else
                {
                    editFlag = true;
                    visibility = View.VISIBLE;
                }
                for (int i=0; i<taskCollection.getCollection().size(); i++)
                {
                    taskListRv.getChildAt(i).findViewById(R.id.del_btn).setVisibility(visibility);
                }
            }
        });
    }

    public void ifCollectionEmpty()
    {
        if(taskCollection.getCollection().size() == 0)
        {
            taskListRv.setVisibility(View.INVISIBLE);
        }
        else
        {
            empImg.setVisibility(View.GONE);
            empTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        storage.updateCollection(taskCollection);
    }

    @Override
    public void taskClicked(int index) {
        Intent i = new Intent(MainActivity.this, CreateList.class);
        i.putExtra("task", listAdapter.getCollection().getTask(index).toString());
        i.putExtra("taskIndex", index);
        startActivity(i);
        finish();
    }
}