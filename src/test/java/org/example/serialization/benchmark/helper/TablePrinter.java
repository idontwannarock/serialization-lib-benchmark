package org.example.serialization.benchmark.helper;

import static org.example.serialization.benchmark.util.FormatUtils.*;

public class TablePrinter {

    private final static String RESULT_FORMAT = "| %-21s | %-5s | %-11s | %-22s | %-21s | %-24s | %-23s | %-16s |";

    private final int ROUNDS;

    public TablePrinter(int rounds) {
        this.ROUNDS = rounds;
    }

    public void printHeader() {
        System.out.println("Executed " + formatWithThousandSeparator(ROUNDS) + " rounds (serialization + deserialization) of each serializers.");
        System.out.println("All time units are in second.");
        System.out.println(formatResult(RESULT_FORMAT, "Serialization Library", "Total", "Avg.", "Total In Serialization", "Avg. In Serialization", "Total In Deserialization", "Avg. In Deserialization", "Max. Used Memory"));
    }

    public void printResult(String serializer, long totalSerializationCostInMillis, long totalDeserializationCostInMillis, long maxUsedMemoryInBytes) {
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

        // max used memory during execution
        String maxUsedMemory = formatWithThousandSeparator(maxUsedMemoryInBytes);

        System.out.println(formatResult(RESULT_FORMAT, serializer, cost, avgCost, serCost, avgSerCost, desCost, avgDesCost, maxUsedMemory));
    }
}
