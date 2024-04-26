package de.storchp.opentracks.osmplugin;

import java.time.LocalDate;

public class AggregateDailyData {
    LocalDate date;
    int runs;
    double distance;
    double duration;

    public AggregateDailyData(LocalDate date, int runs, double distance, double duration) {
        this.date = date;
        this.runs = runs;
        this.distance = distance;
        this.duration = duration;
    }
}