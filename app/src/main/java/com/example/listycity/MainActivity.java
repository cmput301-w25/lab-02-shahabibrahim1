package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button add = findViewById(R.id.button1);
        Button remove = findViewById(R.id.button2);
        EditText cityInput = findViewById(R.id.city_input);
        Button confirmButton = findViewById(R.id.confirm_button);

        cityList = findViewById(R.id.city_list);
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        //Parent - Where the click happened (Listview) container holding all items
        //View - Provides access to the UI element that was interacted with.
        //Position - The index of the clicked item (Start from 0)
        //id - Rarely used in simple list setups but can be useful for custom adapters where the row ID might differ.
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position; // Track the selected position
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (selectedPosition != -1) {
                    dataList.remove(selectedPosition);
                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1;
                }            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityInput.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
            }
        });
        //We add city and make input disapear
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityInput.getText().toString().trim();

                if (!cityName.isEmpty()) {
                    //Add to list
                    dataList.add(cityName);
                    //we tell our UI weve updated list
                    cityAdapter.notifyDataSetChanged();
                    //clear for next input
                    cityInput.setText("");
                    Toast.makeText(MainActivity.this, cityName + " added!", Toast.LENGTH_SHORT).show();
                }
                cityInput.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
            }

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}