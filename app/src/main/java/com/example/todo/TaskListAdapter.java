package com.example.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHodler> {
    TaskCollection collection;
    Click c;

    public interface Click
    {
        public void taskClicked(int index);
    }

    public TaskListAdapter(MainActivity context, TaskCollection collection) {
        this.collection = collection;
        this.c = (Click) context;
    }

    @NonNull
    @Override
    public TaskListViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskListViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHodler holder, int position) {
        Task t = collection.getTask(position);
        if(t.getDone())
        {
           holder.taskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
           holder.itemList.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.taskName.setText(t.getName());
        holder.itemList.setText(t.getItemCollection().toStringDisplay());
        holder.taskTime.setText(t.getTime());
        int p = t.getPriority();
        int resource = 0;
        if(p == 0)
        {
            //High
            resource = R.drawable.red;
        }
        else if(p == 1)
        {
            //High
            resource = R.drawable.green;
        }
        else
        {
            //High
            resource = R.drawable.yellow;
        }
        holder.priorityTag.setImageResource(resource);


        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection.getCollection().remove(position);
                notifyDataSetChanged();
            }
        });

        holder.taskWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.taskClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collection.getCollection().size();
    }

    public class TaskListViewHodler extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskTime;
        TextView itemList;
        ImageView priorityTag;
        ImageView delBtn;
        LinearLayout taskWrapper;

        public TaskListViewHodler(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.task_name_tv);
            itemList = itemView.findViewById(R.id.item_list_tv);
            taskTime = itemView.findViewById(R.id.task_time);
            priorityTag = itemView.findViewById(R.id.priority_view);
            delBtn = itemView.findViewById(R.id.del_btn);
            taskWrapper = itemView.findViewById(R.id.task_wrapper);
        }
    }

    public TaskCollection getCollection() {
        return collection;
    }
}
