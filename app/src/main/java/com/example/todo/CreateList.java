package com.example.todo;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateList extends AppCompatActivity{
    private static final String TIME_FORMAT = "h:mm a";
    TextView timeTv;
    TextView dateTv;
    int priority;
    RadioButton highP;
    RadioButton medP;
    RadioButton lowP;
    EditText listName;
    ImageView timePickerBtn;
    ImageView datePickerBtn;
    ImageView repeatBtn;
    ImageView closeBtn;
    ImageView editItemsBtn;
    Button saveBtn;
    Boolean doRepeat;
    ItemListFrag itemListFrag;
    ItemCollection itemCollection;
    FileStorage storage;
    Boolean isRead;
    Boolean isEditMode;
    int taskToReadIndex;
    int year,month,day;
    int hour, minute;
    String shift;
    Date date;
    Date time;
    final static String DATE_FORMAT = "yyyy-mm-d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        getSupportActionBar().hide();

        init();
        findViewById(R.id.add_item_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateList.this, AddNewItem.class);
                startActivityForResult(i,1);
            }
        });

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = Integer.parseInt(new SimpleDateFormat("h").format(time));
                minute = Integer.parseInt(new SimpleDateFormat("mm").format(time));
                shift = new SimpleDateFormat("a").format(time);
                TimePickerDialog tp = new TimePickerDialog(CreateList.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                try {
                                    time = new SimpleDateFormat("h:mm").parse(hourOfDay+":"+minute);
                                    timeTv.setText(new SimpleDateFormat(TIME_FORMAT).format(time));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },hour,minute,false);
                tp.show();
            }
        });

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Calendar c = Calendar.getInstance();
                    year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
                    month = Integer.parseInt(new SimpleDateFormat("mm").format(date));
                    day = Integer.parseInt(new SimpleDateFormat("d").format(date));
                    DatePickerDialog dp = new DatePickerDialog(CreateList.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    try {
                                        date = new SimpleDateFormat(DATE_FORMAT).parse(year+"-"+month+"-"+dayOfMonth);
                                        dateTv.setText(new SimpleDateFormat(DATE_FORMAT).format(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, year, month, day);
                    dp.show();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listName.getText().toString().isEmpty())
                {
                    listName.setError("Enter List Name.");
                }
                else
                {
                    //Check if all items are set to done
                    Boolean taskDone = true;
                    for (int i=0; i<itemCollection.getCollection().size(); i++)
                    {
                        if(itemCollection.getItem(i).getStatus() == false)
                        {
                            taskDone = false;
                            break;
                        }
                    }
                    Task task = new Task(listName.getText().toString(), itemCollection,
                            timeTv.getText().toString(), dateTv.getText().toString(),
                            priority, doRepeat,taskDone);
                    if(isRead)
                    {
                        storage.updateCollection(taskToReadIndex,task);
                    }
                    else{
                        storage.addRecord(task);
                    }
                    Toast.makeText(getBaseContext(), "List Saved", Toast.LENGTH_LONG);
                    startActivity(new Intent(CreateList.this, MainActivity.class));
                    finish();
                }
            }
        });

        editItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = 0;
                if(isEditMode)
                {
                    isEditMode = false;
                    visibility = View.GONE;
                }
                else
                {
                    isEditMode = true;
                    visibility = View.VISIBLE;
                }
                for (int i=0; i<itemCollection.getCollection().size(); i++)
                {
                    itemListFrag.getRv().getChildAt(i).findViewById(R.id.remove_item_btn).setVisibility(visibility);
                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateList.this, MainActivity.class));
                finish();
            }
        });

        repeatBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!doRepeat)
                {
                    repeatBtn.setImageTintList(ColorStateList.valueOf(Color.BLUE));
                    Toast.makeText(getBaseContext(), "Repeat Onn", Toast.LENGTH_SHORT).show();
                    doRepeat = true;

                }
                else
                {
                    repeatBtn.setImageTintList(ColorStateList.valueOf(Color.GRAY));
                    Toast.makeText(getBaseContext(), "Repeat Off", Toast.LENGTH_SHORT).show();
                    doRepeat = false;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 1)
        {
            if(data != null)
            {
                itemCollection.addItem(new Item(data.getStringExtra("itemName")));
                itemListFrag.getRv().getAdapter().notifyDataSetChanged();
                itemListFrag.empIV.setVisibility(View.GONE);
                itemListFrag.empTV.setVisibility(View.GONE);
            }
        }

    }

    public void init()
    {
        highP = findViewById(R.id.high_pr);
        medP = findViewById(R.id.med_pr);
        lowP = findViewById(R.id.low_pr);
        closeBtn = findViewById(R.id.close_btn);
        editItemsBtn = findViewById(R.id.edit_items_btn);
        isEditMode = false;
        timeTv = findViewById(R.id.time_tv);
        dateTv = findViewById(R.id.date_tv);
        //Set Current Date and Time by default
        Calendar calendar = Calendar.getInstance();
        dateTv.setText(new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime()));
        timeTv.setText(new SimpleDateFormat(TIME_FORMAT).format(calendar.getTime()));
        date = Calendar.getInstance().getTime();
        time = Calendar.getInstance().getTime();
        listName = findViewById(R.id.list_name_inp);
        timePickerBtn = findViewById(R.id.time_btn);
        datePickerBtn = findViewById(R.id.date_picker_btn);
        saveBtn = findViewById(R.id.save_btn);
        repeatBtn = findViewById(R.id.repeat_btn);
        storage = new FileStorage(getBaseContext());
        itemCollection = new ItemCollection();
        itemListFrag = (ItemListFrag) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        doRepeat = false;

        //Check if open for reading
        if(getIntent().getStringExtra("task") != null)
        {
            isRead = true;
            closeBtn.setVisibility(View.GONE);
            editItemsBtn.setVisibility(View.VISIBLE);
            Task t = Task.generateTask(getIntent().getStringExtra("task").toString());
            listName.setText(t.getName());
            itemCollection = t.getItemCollection();
            dateTv.setText(t.getDate());
            timeTv.setText(t.getTime());
            try {
                date = new SimpleDateFormat(DATE_FORMAT).parse(t.getDate());
                time = new SimpleDateFormat(TIME_FORMAT).parse(t.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            doRepeat = t.getRepeat();
            priority = t.getPriority();
            if(priority == 0)
            {
                highP.setChecked(true);
            }
            else if(priority == 1)
            {
                medP.setChecked(true);
            }
            else
            {
                lowP.setChecked(true);
            }
            taskToReadIndex = getIntent().getIntExtra("taskIndex", -1);
            ItemListAdapter listAdapter = (ItemListAdapter) itemListFrag.getRv().getAdapter();
            listAdapter.setReadMode(true);
        }
        else
        {
            isRead = false;
        }
        itemListFrag.setCollection(itemCollection);

        highP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = 0;
            }
        });
        medP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = 1;
            }
        });
        lowP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = 2;
            }
        });
    }
}