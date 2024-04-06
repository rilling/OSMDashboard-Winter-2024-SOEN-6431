package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.util.Log;
import androidx.core.util.Pair;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import de.storchp.opentracks.osmplugin.dashboardapi.TrackPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Option3 extends MapsActivity {
    public static final List<Pair<Double, String>> speedTimeEntries = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option1);

        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        Map<Integer, Integer> hourCounts = new HashMap<>();

        // Iterate through each entry to count occurrences by hour
        for (android.util.Pair<Double, String> entry : TrackPoint.speedTimeEntries) {
            String time = entry.second; // Get the time string
            Log.d("time", String.valueOf(entry));

            try {
                Date date = sdf.parse(time); // Parse the time string to a Date object
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                hourCounts.put(hour, hourCounts.getOrDefault(hour, 0) + 1);
            } catch (ParseException e) {
                Log.e("TrackPointTime", "Error parsing timestamp: " + time, e);
            }
        }

        // Convert the map keys (hours) to a sorted list and then create bar entries
        List<Integer> sortedHours = new ArrayList<>(hourCounts.keySet());
        java.util.Collections.sort(sortedHours);
        for (int hour : sortedHours) {
            entries.add(new BarEntry(hour, hourCounts.get(hour)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Data Recording Frequency by Hour");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customize the X-Axis to show hour labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%02d:00", (int) value);
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(entries.size());

        Description description = new Description();
        description.setText("Hourly Data Recording Distribution");
        description.setTextSize(16f);
        barChart.setDescription(description);

        barChart.invalidate(); // Refresh the chart
    }
}