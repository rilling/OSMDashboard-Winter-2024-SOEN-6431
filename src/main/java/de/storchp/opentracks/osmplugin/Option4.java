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
    
    private void setUpChart(LineChart lineChart, List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Moving Average Speed");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

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
        lineChart.invalidate();
    }

}
