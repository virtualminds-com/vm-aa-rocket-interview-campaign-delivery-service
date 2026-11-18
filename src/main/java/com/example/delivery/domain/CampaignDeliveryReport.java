package com.example.delivery.domain;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * Aggregated delivery for a campaign: the daily rows plus totals.
 */
public record CampaignDeliveryReport(
        Campaign campaign,
        List<DailyDelivery> days,
        long totalImpressions,
        long totalClicks,
        BigDecimal totalSpend) {

    public static CampaignDeliveryReport from(Campaign campaign, List<DailyDelivery> days) {
        List<DailyDelivery> sorted = days.stream()
                .sorted(Comparator.comparing(DailyDelivery::date))
                .toList();

        long impressions = sorted.stream().mapToLong(DailyDelivery::impressions).sum();
        long clicks = sorted.stream().mapToLong(DailyDelivery::clicks).sum();
        BigDecimal spend = sorted.stream()
                .map(DailyDelivery::spend)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CampaignDeliveryReport(campaign, sorted, impressions, clicks, spend);
    }
}
