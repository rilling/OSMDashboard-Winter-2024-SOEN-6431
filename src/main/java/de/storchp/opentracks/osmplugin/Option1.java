package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import de.storchp.opentracks.osmplugin.dashboardapi.TrackPoint;

import java.util.ArrayList;

public class Option1 extends MapsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option1);

        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> entries = new ArrayList<>();

        // Define speed ranges (0-1 km/h, 1-2 km/h, ..., 9-10 km/h)
        int numSpeedRanges = 16;
        int[] speedCounts = new int[numSpeedRanges];

        // Iterate through the static list from TrackPoint class
        Log.d("count", String.valueOf(TrackPoint.speedTimeEntries.size()));
        for (int i = 0; i < TrackPoint.speedTimeEntries.size(); i++) {
            double speed = TrackPoint.speedTimeEntries.get(i).first;
            int speedRangeIndex = (int) Math.floor(speed);
            if (speedRangeIndex >= 0 && speedRangeIndex < numSpeedRanges) {
                speedCounts[speedRangeIndex]++;
            }
        }

        for (int i = 0; i < numSpeedRanges; i++) {
            entries.add(new BarEntry(i, speedCounts[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Speed Distribution");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        String[] speedRanges = new String[numSpeedRanges];
        for (int i = 0; i < numSpeedRanges; i++) {
            speedRanges[i] = i + "-" + (i + 1) + " km/h";
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(speedRanges));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(numSpeedRanges);

        Description description = new Description();
        description.setText("Speed Distribution");
        description.setTextSize(16f);
        barChart.setDescription(description);

        barChart.invalidate(); // Refresh the chart
    }
}