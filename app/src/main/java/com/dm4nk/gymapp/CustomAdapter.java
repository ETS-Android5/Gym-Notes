package com.dm4nk.gymapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dm4nk.gymapp.domain.Exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {

    public static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    private final Context context;
    private final Activity activity;
    private final List<Exercise> exerciseList;
    private final List<Exercise> exerciseListFull;
    private final Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Exercise> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exerciseListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim();

                for (Exercise e : exerciseListFull) {
                    if (e.getName().toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(e);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exerciseList.clear();
            exerciseList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public CustomAdapter(Activity activity, Context context, List<Exercise> exerciseList) {
        this.activity = activity;
        this.context = context;
        this.exerciseList = exerciseList;
        this.exerciseListFull = new ArrayList<>(exerciseList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(String.valueOf(exerciseList.get(position).getName()));
        holder.sets.setText(String.valueOf(exerciseList.get(position).getSets()));
        holder.reps.setText(String.valueOf(exerciseList.get(position).getReps()));
        holder.weight.setText(String.valueOf(exerciseList.get(position).getWeight()));
        holder.date.setText(String.valueOf(format.format(exerciseList.get(position).getDate())));

        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(exerciseList.get(position).getId()));
            intent.putExtra("name", String.valueOf(exerciseList.get(position).getName()));
            intent.putExtra("sets", String.valueOf(exerciseList.get(position).getSets()));
            intent.putExtra("reps", String.valueOf(exerciseList.get(position).getReps()));
            intent.putExtra("weight", String.valueOf(exerciseList.get(position).getWeight()));
            intent.putExtra("url", String.valueOf(exerciseList.get(position).getUrl()));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, sets, reps, weight, date;
        //todo: Linear layout
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            sets = itemView.findViewById(R.id.sets);
            reps = itemView.findViewById(R.id.reps);
            weight = itemView.findViewById(R.id.weight);
            date = itemView.findViewById(R.id.date);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
