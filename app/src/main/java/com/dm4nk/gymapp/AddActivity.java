package com.dm4nk.gymapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dm4nk.gymapp.domain.Exercise;

public class AddActivity extends AppCompatActivity {

    EditText name_input, sets_input, reps_input, weight_input, url_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        sets_input = findViewById(R.id.sets_input);
        reps_input = findViewById(R.id.reps_input);
        url_input = findViewById(R.id.url_input);
        weight_input = findViewById(R.id.weight_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            DatabaseHelper myDB = new DatabaseHelper(AddActivity.this);
            int sets;
            int reps;

            try {
                sets = Integer.parseInt(sets_input.getText().toString().trim());
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
                    Exercise.builder()
                            .name(name_input.getText().toString().trim())
                            .sets(sets)
                            .reps(reps)
                            .weight(weight_input.getText().toString().trim())
                            .url(url_input.getText().toString().trim())
                            .build()
            );
            finish();
        });
    }
}