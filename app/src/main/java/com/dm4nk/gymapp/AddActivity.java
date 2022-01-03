package com.dm4nk.gymapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText name_input, times_input, reps_input, weight_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        times_input = findViewById(R.id.times_input);
        reps_input = findViewById(R.id.reps_input);
        weight_input = findViewById(R.id.weight_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
            int times;
            int reps;

            try {
                times = Integer.parseInt(times_input.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wrong times format", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                reps = Integer.parseInt(reps_input.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wrong times format", Toast.LENGTH_SHORT).show();
                return;
            }

            myDB.addExercise(
                    name_input.getText().toString().trim(),
                    times,
                    reps,
                    weight_input.getText().toString().trim()
            );
            finish();
        });
    }
}