package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.util.Pair;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import de.storchp.opentracks.osmplugin.dashboardapi.TrackPoint;

import java.util.ArrayList;

public class Option2 extends MapsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option2); // Ensure this layout exists and has a BarChart with the correct ID

        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> entries = new ArrayList<>();

        // Speed range definitions
        int numSpeedRanges = 16;
        double[] elevationSums = new double[numSpeedRanges];
        int[] trackPointCounts = new int[numSpeedRanges];

        // Calculate sums and counts
        for (Pair<Double, Double> entry : TrackPoint.speedElevationEntries) {
            double speed = entry.first;
            double elevation = entry.second;
            int index = (int) Math.floor(speed);
            if (index >= 0 && index < numSpeedRanges) {
                elevationSums[index] += elevation;
                trackPointCounts[index]++;
            }
        }

        // Calculate averages and create bar entries
        for (int i = 0; i < numSpeedRanges; i++) {
            float avgElevation = trackPointCounts[i] > 0 ? (float) (elevationSums[i] / trackPointCounts[i]) : 0;
            entries.add(new BarEntry(i, avgElevation));
        }

        // Remaining plotting logic is similar...
        BarDataSet dataSet = new BarDataSet(entries, "Speed Range");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Configure the chart as before
        // ...
        XAxis xAxis = barChart.getXAxis();
        String[] speedRanges = new String[numSpeedRanges];
        for (int i = 0; i < numSpeedRanges; i++) {
            speedRanges[i] = i + "-" + (i + 1) + " km/h";
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(speedRanges));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(numSpeedRanges);
        xAxis.setLabelRotationAngle(45);


        Description description = new Description();
        description.setText("");
        description.setTextSize(16f);
        barChart.setDescription(description);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) barChart.getLayoutParams();
        layoutParams.setMargins(0, 0, 100, 0); // Adjust right margin as needed
        barChart.setLayoutParams(layoutParams);

        barChart.invalidate(); // Refresh the chart
    }


}