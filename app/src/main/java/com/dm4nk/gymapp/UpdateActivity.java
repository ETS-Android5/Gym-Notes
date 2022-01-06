package com.dm4nk.gymapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    private EditText name_input2, sets_input2, reps_input2, weight_input2;
    private Button update_button, delete_button;
    private String id, name, sets, reps, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input2 = findViewById(R.id.name_input2);
        sets_input2 = findViewById(R.id.sets_input2);
        reps_input2 = findViewById(R.id.reps_input2);
        weight_input2 = findViewById(R.id.weight_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(view -> {
            DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
            String new_name = name_input2.getText().toString().trim();
            String new_times = sets_input2.getText().toString().trim();
            String new_reps = reps_input2.getText().toString().trim();
            String new_weight = weight_input2.getText().toString().trim();

            if (new_name.equals(name) &&
                    new_times.equals(sets) &&
                    new_reps.equals(reps) &&
                    new_weight.equals(weight)) {
                Toast.makeText(this, "Nothing changed", Toast.LENGTH_SHORT).show();
                return;
            }

            int times;
            int reps;

            try {
                times = Integer.parseInt(new_times);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wrong times format", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                reps = Integer.parseInt(new_reps);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wrong times format", Toast.LENGTH_SHORT).show();
                return;
            }

            myDB.updateData(id, new_name, times, reps, new_weight);
            finish();
        });

        delete_button.setOnClickListener(view -> {
            confirmDialog();
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("name") &&
                getIntent().hasExtra("sets") &&
                getIntent().hasExtra("reps") &&
                getIntent().hasExtra("weight")) {
            //getting data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            sets = getIntent().getStringExtra("sets");
            reps = getIntent().getStringExtra("reps");
            weight = getIntent().getStringExtra("weight");

            //setting data
            name_input2.setText(name);
            sets_input2.setText(sets);
            reps_input2.setText(reps);
            weight_input2.setText(weight);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
            myDB.deleteOneRow(id);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });
        builder.create().show();
    }
}