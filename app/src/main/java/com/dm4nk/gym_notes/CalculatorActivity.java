package com.dm4nk.gym_notes;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelFormatException;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.zip.Inflater;

public class CalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final Integer[] PATHS = {0, 5, 10, 15, 20, 25, 30};
    private static final Double[] PLATES = {1.25, 2.5, 5., 10., 15., 20., 25., 25., 25., 25., 25., 25., 25.};
    private static final double POUNDS_IN_KG = 2.205;
    private static final String FORMAT_1 = "%.1f";
    private static final String FORMAT_2 = "%.2f";
    private Spinner spinner;
    private EditText input_weight_calculator;
    private TextView answer_pounds, answer_kilos, barbell_weight_1, dumbbell_weight;
    private int position = 0;
    private ImageView one, two, five, ten, fifteen, twenty, twenty_five, twenty_five2, twenty_five3, twenty_five4, twenty_five5, twenty_five6, twenty_five7;
    private ImageView[] PLATES_IMG;

    private LinearLayout scrollViewLinearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        spinner = findViewById(R.id.spinner);
        input_weight_calculator = findViewById(R.id.input_weight_calculator);
        answer_pounds = findViewById(R.id.answer_pounds);
        answer_kilos = findViewById(R.id.answer_kilos);
        barbell_weight_1 = findViewById(R.id.barbell_weight_1);
        dumbbell_weight = findViewById(R.id.dumbbell_weight);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        five = findViewById(R.id.five);
        ten = findViewById(R.id.ten);
        fifteen = findViewById(R.id.fifteen);
        twenty = findViewById(R.id.twenty);
        twenty_five = findViewById(R.id.twenty_five);
        twenty_five2 = findViewById(R.id.twenty_five2);
        twenty_five3 = findViewById(R.id.twenty_five3);
        twenty_five4 = findViewById(R.id.twenty_five4);
        twenty_five5 = findViewById(R.id.twenty_five5);
        twenty_five6 = findViewById(R.id.twenty_five6);
        twenty_five7 = findViewById(R.id.twenty_five7);

        PLATES_IMG = new ImageView[]{one, two, five, ten, fifteen, twenty, twenty_five, twenty_five2, twenty_five3, twenty_five4, twenty_five5, twenty_five6, twenty_five7};

        scrollViewLinearlayout = (LinearLayout)findViewById(R.id.scroll_view_linear_layout);

        prepareImages();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(CalculatorActivity.this, android.R.layout.simple_spinner_dropdown_item, PATHS);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        input_weight_calculator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prepareCalculation(position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.calculator_label);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        prepareCalculation(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.position = 0;
    }

    private void prepareCalculation(int position) {
        if (!input_weight_calculator.getText().toString().trim().isEmpty()) {
            try {
                double weight = Double.parseDouble(input_weight_calculator.getText().toString().trim());
                calculate(PATHS[position], weight);
            }
            catch (NumberFormatException e){
                Toast.makeText(this, "Wrong number format", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void calculate(int percent, double weight) {
        double kilos_num = weight / POUNDS_IN_KG;
        double pounds_num = weight * POUNDS_IN_KG;

        double dumbbell_num = (Math.ceil(weight * ((100 - percent) / 100.) / 2.5)) * 1.25;
        double barbell_num = dumbbell_num - 10;

        dumbbell_num = Math.max(dumbbell_num, 0);
        barbell_num = Math.max(barbell_num, 0);

        answer_kilos.setText(String.format(FORMAT_1, kilos_num));
        answer_pounds.setText(String.format(FORMAT_1, pounds_num));
        barbell_weight_1.setText(String.format(FORMAT_2, barbell_num));
        dumbbell_weight.setText(String.format(FORMAT_2, dumbbell_num));

        prepareImages();
        updatePlates(barbell_num, 12);
    }

    private void prepareImages(){
        for(ImageView imageView : PLATES_IMG){
            if(imageView.getParent() != null) {
                ((ViewGroup)imageView.getParent()).removeView(imageView);
            }
        }
    }

    private void updatePlates(double weight, int plate){
        if(plate < 0) return;

        double actualWeight = weight;

        if(weight >=  PLATES[plate]){
            actualWeight = weight-PLATES[plate];
            scrollViewLinearlayout.addView(PLATES_IMG[plate]);
        }
        else {
            scrollViewLinearlayout.removeView(PLATES_IMG[plate]);
        }

        updatePlates(actualWeight, plate-1);
    }
}