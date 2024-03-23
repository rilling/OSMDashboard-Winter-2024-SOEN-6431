package de.storchp.opentracks.osmplugin;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class option4 extends MapsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option4);

        // Find the LineChart in the layout
        LineChart lineChart = findViewById(R.id.lineChart);

        // Create sample data for the line chart
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 10));
        entries.add(new Entry(2, 20));
        entries.add(new Entry(3, 15));
        entries.add(new Entry(4, 25));
        entries.add(new Entry(5, 30));

        // Create a LineDataSet with the sample data
        LineDataSet dataSet = new LineDataSet(entries, "Sample Data");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);

        // Create a LineData object with the LineDataSet
        LineData lineData = new LineData(dataSet);

        // Set the LineData to the LineChart
        lineChart.setData(lineData);

        // Customize the LineChart as needed
        Description description = new Description();
        description.setText("Sample Line Chart");
        lineChart.setDescription(description);
        lineChart.animateXY(1000, 1000);
        lineChart.invalidate(); // Refresh the chart
    }
}
