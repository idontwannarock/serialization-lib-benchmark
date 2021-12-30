package com.example.serialization.benchmark.helper;

import static com.example.serialization.benchmark.util.FormatUtils.*;

public class TablePrinter {

    private final int ROUNDS;

    public TablePrinter(int rounds) {
        this.ROUNDS = rounds;
    }

    public void printHeader() {
        System.out.println("Executed " + formatWithThousandSeparator(ROUNDS) + " rounds (serialization + deserialization) of each serializers.");
        System.out.println("All time units are in second.");
        System.out.println(formatResult("Serializer", "Tot. Time", "Avg. Time", "Tot. Ser. Time", "Avg. Ser. Time", "Tot. Des. Time", "Avg. Des. Time"));
    }

    public void printResult(String serializer, long totalSerializationCostInMillis, long totalDeserializationCostInMillis) {
        long totalCostTimeInMilliseconds = totalSerializationCostInMillis + totalDeserializationCostInMillis;

        // total and average time in seconds
        String cost = formatTotalDuration((double) totalCostTimeInMilliseconds / 1000);
        String avgCost = formatAverageDuration((double) totalCostTimeInMilliseconds / 1000 / ROUNDS);

        // total and average time of serialization in seconds
        String serCost = formatTotalDuration((double) totalSerializationCostInMillis / 1000);
        String avgSerCost = formatAverageDuration((double) totalSerializationCostInMillis / 1000 / ROUNDS);

        // total and average time of deserialization in seconds
        String desCost = formatTotalDuration((double) totalDeserializationCostInMillis / 1000);
        String avgDesCost = formatAverageDuration((double) totalDeserializationCostInMillis / 1000 / ROUNDS);

        System.out.println(formatResult(serializer, cost, avgCost, serCost, avgSerCost, desCost, avgDesCost));
    }
}
