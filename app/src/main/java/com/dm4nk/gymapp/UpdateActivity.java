package com.dm4nk.gymapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dm4nk.gymapp.domain.Exercise;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    private EditText name_input2, sets_input2, reps_input2, weight_input2, url_input2;
    private Button update_button, delete_button;
    private ImageView you_tube_image;
    private String id, name, sets, reps, weight, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input2 = findViewById(R.id.name_input2);
        sets_input2 = findViewById(R.id.sets_input2);
        reps_input2 = findViewById(R.id.reps_input2);
        weight_input2 = findViewById(R.id.weight_input2);
        url_input2 = findViewById(R.id.url_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        you_tube_image = findViewById(R.id.you_tube_image);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(view -> {
            DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
            String new_name = name_input2.getText().toString().trim();
            String new_sets = sets_input2.getText().toString().trim();
            String new_reps = reps_input2.getText().toString().trim();
            String new_weight = weight_input2.getText().toString().trim();
            String new_url = url_input2.getText().toString().trim();
            Long date;

            if (new_name.equals(name) &&
                    new_sets.equals(sets) &&
                    new_reps.equals(reps) &&
                    new_weight.equals(weight) &&
                    new_url.equals(url)) {
                Toast.makeText(this, "Nothing changed", Toast.LENGTH_SHORT).show();
                return;
            }

            if (
                    new_sets.equals(sets) &&
                            new_reps.equals(reps) &&
                            new_weight.equals(weight)) {
                date = null;
            } else {
                date = Calendar.getInstance().getTimeInMillis();
            }

            int sets;
            int reps;

            try {
                sets = Integer.parseInt(new_sets);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wrong sets format", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                reps = Integer.parseInt(new_reps);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Wrong sets format", Toast.LENGTH_SHORT).show();
                return;
            }

            myDB.updateData(
                    Exercise.builder()
                            .id(Integer.valueOf(id))
                            .name(new_name)
                            .sets(sets)
                            .reps(reps)
                            .weight(new_weight)
                            .date(date)
                            .url(new_url)
                            .build()
            );
            finish();
        });

        you_tube_image.setOnClickListener(v -> {
            if (url.equals("")) {
                Toast.makeText(this, "No YouTube URL", Toast.LENGTH_SHORT).show();
            } else {
                Uri uri = Uri.parse(url);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
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
                getIntent().hasExtra("weight") &&
                getIntent().hasExtra("url")) {
            //getting data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            sets = getIntent().getStringExtra("sets");
            reps = getIntent().getStringExtra("reps");
            weight = getIntent().getStringExtra("weight");
            url = getIntent().getStringExtra("url");

            //setting data
            name_input2.setText(name);
            sets_input2.setText(sets);
            reps_input2.setText(reps);
            weight_input2.setText(weight);
            url_input2.setText(url);
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