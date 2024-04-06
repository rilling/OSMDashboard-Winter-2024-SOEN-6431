package de.storchp.opentracks.osmplugin;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import de.storchp.opentracks.osmplugin.dashboardapi.TrackPoint;


public class Option4 extends MapsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option4);

        // Setup the chart
        LineChart chart = findViewById(R.id.lineChart);
        Spinner windowSizeSpinner = findViewById(R.id.spinner_window_size);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.window_size_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        windowSizeSpinner.setAdapter(adapter);

        windowSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int windowSize = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                List<Entry> movingAverageEntries = getMovingAverageEntries(windowSize);
                setUpChart(chart, movingAverageEntries);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
//                if(movingAverageEntries != null && chart != null) {
//                    List<Entry> movingAverageEntries = getMovingAverageEntries(speeds, 5); // Using 5 as the default window size
//                    setUpChart(chart, movingAverageEntries);
//                }
            }
        });

    }

    private List<Entry> getMovingAverageEntries(int windowSize) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < TrackPoint.speedTimeEntries.size(); i++) {
            if (i >= windowSize - 1) {
                float sum = 0;
                for (int j = i - (windowSize - 1); j <= i; j++) {
                    sum += TrackPoint.speedTimeEntries.get(j).first.floatValue();
                }
                float average = sum / windowSize;
                entries.add(new Entry(i, average));
            }
        }
        return entries;
    }
}
