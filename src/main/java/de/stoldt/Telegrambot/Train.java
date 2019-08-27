package de.stoldt.Telegrambot;

import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class Train {

    private static final int ONE_MINUTE_IN_MILLIS = 60000;
    private static final int MAX_RAIL_NUMBER = 14;
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
        String result = "\n*" + dateFormat.format(time) + "* - " + trainLine + " nach " + normalizedDestination;
        if (cancelled) {
            result += (shuttleService)
                    ? ("\n*ACHTUNG:* Zug fällt aus. Schienenersatzverkehr ist eingerichtet.")
                    : ("\n*ACHTUNG:* Zug fällt aus. Es ist kein Schienenersatzverkehr eingerichtet.");
        } else {
            boolean delayed = forecastMin > 5;
            result += (delayed)
                    ? (" (*+" + forecastMin + "min*)\n")
                    : (" (+" + forecastMin + "min)\n");
            if (rail < MAX_RAIL_NUMBER) result += "fährt von Gleis: " + rail;
            if (delayed)
                result += " | voraussichtlich " + dateFormat.format(new Date(time.getTime() + forecastMin * ONE_MINUTE_IN_MILLIS));
        }
        return result + "\n";
    }
}
