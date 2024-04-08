package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Filter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private final String[] filterOptions = {"Week", "Month", "Season"};
    private static int selectedFilterOption = 7; // Default value to 7

    public static int getBarSize() {
        return selectedFilterOption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterbar);

        // Initialize Spinner
        spinner = findViewById(R.id.spinner2);
        if (spinner != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, filterOptions);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0 -> selectedFilterOption = 7;
            case 1 -> selectedFilterOption = 12;
            case 2 -> selectedFilterOption = 3;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedFilterOption = 7; // Default to 7 if nothing is selected
    }
}
