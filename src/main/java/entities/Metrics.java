package main.java.entities;

import java.time.LocalDate;
import java.util.List;

import main.java.Constants;

/**
 * Metrics: the metrics of a stock.
 */
public class Metrics {
    private final SharePrices sharePrices;
    private final MetricValues earnings;
    private final MetricValues volumes;

    public Metrics(SharePrices sharePrices,
                   MetricValues earnings,
                   MetricValues volumes) {
        this.sharePrices = sharePrices;
        this.earnings = earnings;
        this.volumes = volumes;
    }

    /**
     * Get share price on date
     * @param date the date. If there is no value for the date, return the value on
     *             the date immediately after it.
     * @return share price
     */
    public Double sharePrice(LocalDate date) {
        return sharePrices.getValue(date);
    }

    /**
     * Get volume on date.
     * @param date the date. If there is no value for the date, return the value
     *             on the date immedidately after it.
     * @return volume at given days back
     */
    public Double volume(LocalDate date) {
        return volumes.getValue(date);
    }

    /**
     * Calculate growth percentage between the stock at the start and
     * end of an interval.
     * @param start the starting date of the interval, inclusive.
     * @param end the end date of the interval, inclusive.
     * @return the growth percentage
     */
    public Double growthPercentage(LocalDate start, LocalDate end) {
        final Double startPrice = sharePrices.getValue(start);
        final Double endPrice = sharePrices.getValue(end);
        return startPrice * Constants.PERCENTAGE / endPrice;
    }

    /**
     * Calculate earnings per share by aggregating earnings between an interval.
     * @param start the starting date of the interval, inclusive.
     * @param end the end date of the interval, inclusive.
     * @return earnings per share
     */
    public Double earningsPerShare(LocalDate start, LocalDate end) {
        return getTotalEarnings(start, end) / sharePrices.getLatest();
    }

    private Double getTotalEarnings(LocalDate start, LocalDate end) {
        final List<Double> earningsInInterval = earnings.getInterval(start, end);
        double total = 0;
        for (Double earning : earningsInInterval) {
            total += earning;
        }
        return total;
    }
}
