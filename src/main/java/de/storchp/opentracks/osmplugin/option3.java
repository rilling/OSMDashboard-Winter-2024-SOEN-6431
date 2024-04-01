package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class option3 extends MapsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option3);

        // Find the BarChart in the layout
        BarChart barChart = findViewById(R.id.barChart);

        // Create a list of bar entries for the data
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (float) (Math.random() * 100))); // Random data for demonstration
        }

        // Create a dataset with the entries
        BarDataSet dataSet = new BarDataSet(entries, "");

//        dataSet.setDrawValues(true); // Enable drawing values on top of bars

        // Set the data set label to be drawn above the bars
//        dataSet.setValueTextSize(12f);
//        dataSet.setValueTextColor(getResources().getColor(R.color.black)); // Customize text color
//        dataSet.setValueFormatter(new IndexAxisValueFormatter("Distance(KM)")); // Customize label format


        // Create a BarData object with the dataset
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customize the x-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getMonths()));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12); // Show all 12 months

        Description description = new Description();
        description.setText("No. of runs");
        description.setTextSize(16f);
        barChart.setDescription(description);



        // Refresh the chart
        barChart.invalidate();
    }

    // Method to get an array of month names
    private String[] getMonths() {
        return new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
    }
}
