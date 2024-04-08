package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.util.Log;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDate;
import java.util.ArrayList;

public class DistanceGraph extends MapsActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distancegraph);
        int size = Filter.getBarSize();

        BarChart barChart = findViewById(R.id.bar1Chart);
        ArrayList<BarEntry> entries = new ArrayList<>();

        int[] barHeight = new int[size];
        String[] timeRanges = new String[size];

        switch (size) {
            case 3 -> { //seasons
                //TODO: Fetch data from group 11
                LocalDate start = LocalDate.now().minusDays(LocalDate.now().getDayOfYear() + 1);
                LocalDate end = LocalDate.now();
                LocalDate startWinter = LocalDate.of(start.getYear() + 1, 1, 1);
                LocalDate startSummer = LocalDate.of(start.getYear(), 5, 1);
                LocalDate startFall = LocalDate.of(start.getYear(), 9, 1);
                while (start.isBefore(end)) {
                    AggregateDailyData data = getRandomData(start);
                    if (start.isBefore(startSummer)) {
                        barHeight[0] += data.distance;
                    } else if (start.isBefore(startFall)) {
                        barHeight[1] += data.distance;
                    } else if (start.isBefore(startWinter)) {
                        barHeight[2] += data.distance;
                    }
                    start.plusDays(1);
                }
                timeRanges = new String[]{"Winter", "Summer", "Fall"};
            }
            case 7 -> { //week
                LocalDate weekStart = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
                LocalDate weekEnd = weekStart.plusDays(7);
                while (weekStart.isBefore(weekEnd)) {
                    AggregateDailyData data = getRandomData(weekStart);
                    int dayOfWeekIndex = weekStart.getDayOfWeek().getValue() - 1; // 0 for Monday, 1 for Tuesday, ..., 6 for Sunday
                    barHeight[dayOfWeekIndex] += data.distance;
                    weekStart = weekStart.plusDays(1);
                }
                timeRanges = new String[]{"M", "T", "W", "T", "F", "S", "S"};
            }
            case 12 -> { // month
                LocalDate startOfYear = LocalDate.now().minusDays(LocalDate.now().getDayOfYear() + 1);
                LocalDate today = LocalDate.now();
                while (startOfYear.isBefore(today)) {
                    AggregateDailyData data = getRandomData(startOfYear);
                    int month = startOfYear.getMonthValue() - 1;
                    barHeight[month] += data.distance;
                    startOfYear = startOfYear.plusDays(1); // Corrected line
                }
                timeRanges = new String[]{"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"};
            }
        }

        for (int i = 0; i < size; i++) {
            entries.add(new BarEntry(i, barHeight[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Distance Distribution");
        dataSet.setColor(Color.RED); // Set the color to red
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setValueFormatter(new IndexAxisValueFormatter(timeRanges));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(size);

        Description description = new Description();
        description.setText("Time Distribution");
        description.setTextSize(16f);
        description.setYOffset(2f); // Adjust the Y offset to move the description down
        barChart.setDescription(description);

        barChart.invalidate(); // Refresh the chart
    }

    private AggregateDailyData getRandomData(LocalDate date) {
        int maxRuns = 40;
        int runs = (int) Math.ceil(Math.random() * maxRuns);
        double distance = (Math.random() * 10 + 10) * runs; //10 km at least per run
        double duration = distance * runs * (Math.random() + 35); //35 min at least per run

        // Logging
        Log.d("DataDebug", "Date: " + date + ", Distance: " + distance + ", Runs: " + runs + ", Duration: " + duration);

        return new AggregateDailyData(date, runs, distance, duration);
    }
}
