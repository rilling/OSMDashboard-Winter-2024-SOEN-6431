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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import de.storchp.opentracks.osmplugin.dashboardapi.TrackPoint;


public class Option4 extends MapsActivity {
    private LineChart chart;
    private List<Entry> movingAverageEntries;
    private List<Entry> timeAverageEntries;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option4);

        // Setup the chart
        chart = findViewById(R.id.lineChart);
        Spinner windowSizeSpinner = findViewById(R.id.spinner_window_size);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.window_size_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        windowSizeSpinner.setAdapter(adapter);

        windowSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int windowSize = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                movingAverageEntries = getMovingAverageEntries(windowSize);
                timeAverageEntries = getTimeAverageEntries(windowSize);
                setUpChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
//                do nothing
            }
        });

    }
        // Setting up chart initially with default window size
        int defaultWindowSize = 5; // Example default window size
        // movingAverageEntries = getMovingAverageEntries(defaultWindowSize);
        // timeAverageEntries = getTimeAverageEntries(defaultWindowSize);
        // setUpChart();



    private List<Entry> getTimeAverageEntries(int windowSize) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        List<Entry> entries = new ArrayList<>();
        for (int i = windowSize - 1; i < TrackPoint.speedTimeEntries.size(); i++) {
            float sum = 0f;
            for (int j = i - (windowSize - 1); j < i; j++) {
                String currentTimeStr = TrackPoint.speedTimeEntries.get(j).second;
                String nextTimeStr = TrackPoint.speedTimeEntries.get(j + 1).second;

                LocalDateTime currentTime = LocalDateTime.parse(currentTimeStr, dateTimeFormatter);
                LocalDateTime nextTime = LocalDateTime.parse(nextTimeStr, dateTimeFormatter);

                Duration duration = Duration.between(currentTime, nextTime);
                float timeInSeconds = (float) duration.getSeconds();
                // Convert time to hours
                float timeInHours = timeInSeconds / 3600; // 1 hour = 3600 seconds
                sum += timeInHours;
            }
            float average = sum / windowSize;
            entries.add(new Entry(i, average));
        }
        return entries;
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
    
  private void setUpChart() {
        setLineData(chart, movingAverageEntries, "Moving Average");
        setLineData(chart, timeAverageEntries, "Time Average");

        // Customizing chart appearance
        customizeChartAppearance(chart);

        // Refresh the chart
        chart.invalidate();
    }

    private void setLineData(@NonNull LineChart lineChart, List<Entry> entries, String label) {
        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(Color.BLUE); // Set the line color to blue
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(Color.BLUE); // Set the circle color to blue
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
    }

    private void customizeChartAppearance(@NonNull LineChart lineChart) {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(Color.LTGRAY);
        xAxis.setGridLineWidth(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12f);
        xAxis.setAxisLineWidth(2f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setGridColor(Color.LTGRAY);
        leftAxis.setGridLineWidth(1f);
        leftAxis.setTextSize(12f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setAxisLineWidth(2f);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(12f);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(10f);
        legend.setXEntrySpace(5f);
        legend.setFormToTextSpace(5f);

        lineChart.setTouchEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        Description description = new Description();
        description.setText("Time");
        lineChart.setDescription(description);
        description.setTextColor(Color.BLACK);
        description.setTextSize(12f);

        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
    }


}
