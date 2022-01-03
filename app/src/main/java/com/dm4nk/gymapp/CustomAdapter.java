package com.dm4nk.gymapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> id;
    private final ArrayList<String> name;
    private final ArrayList<String> times;
    private final ArrayList<String> reps;
    private final ArrayList<String> weight;
    private final ArrayList<String> date;

    public CustomAdapter(Activity activity, Context context, ArrayList<String> id, ArrayList<String> name, ArrayList<String> times, ArrayList<String> reps, ArrayList<String> weight, ArrayList<String> date) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.times = times;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //holder.id.setText(String.valueOf(id.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.times.setText(String.valueOf(times.get(position)));
        holder.reps.setText(String.valueOf(reps.get(position)));
        holder.weight.setText(String.valueOf(weight.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));

        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(id.get(position)));
            intent.putExtra("name", String.valueOf(name.get(position)));
            intent.putExtra("times", String.valueOf(times.get(position)));
            intent.putExtra("reps", String.valueOf(reps.get(position)));
            intent.putExtra("weight", String.valueOf(weight.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, times, reps, weight, date;
        //todo: Linear layout
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            times = itemView.findViewById(R.id.times);
            reps = itemView.findViewById(R.id.reps);
            weight = itemView.findViewById(R.id.weight);
            date = itemView.findViewById(R.id.date);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
