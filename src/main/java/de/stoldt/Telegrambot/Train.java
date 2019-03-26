package de.stoldt.Telegrambot;

import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class Train {

    public static final int ONE_MINUTE_IN_MILLIS = 60000;
    private Date time;
    private String trainLine;
    private Stations destination;
    private String forecast;
    private int forecastMin;
    private int rail;
    private boolean cancelled;
    private boolean shuttleService;

    public Train(Date time, String trainLine, Stations destination, String forecast, int forecastMin, int rail, boolean cancelled, boolean shuttleService) {
        this.time = time;
        this.trainLine = trainLine;
        this.destination = destination;
        this.forecast = forecast;
        this.forecastMin = forecastMin;
        this.rail = rail;
        this.cancelled = cancelled;
        this.shuttleService = shuttleService;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String normalizedDestination = destination.toString().substring(0, 1) + destination.toString().substring(1).toLowerCase().replace("_", " ");
        String result = "\n*" + dateFormat.format(time) + "* - " + trainLine + " nach " + normalizedDestination + " (+" + forecastMin + "min)\n" +
                "fährt von Gleis: " + rail;
        if (forecastMin > 5) {
            result = result.replace("+" + forecastMin + "min", "*+" + forecastMin + "min*");
            Date newTime = new Date(time.getTime() + forecastMin * ONE_MINUTE_IN_MILLIS);
            result = result + " | voraussichtlich " + dateFormat.format(newTime);
        }
        return result + "\n";
    }
}
