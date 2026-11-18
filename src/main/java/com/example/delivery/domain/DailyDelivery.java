package com.example.delivery.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Delivery statistics of one campaign for one day.
 *
 * @param spend spend in the campaign currency, at full precision as reported by the bidder
 */
public record DailyDelivery(String campaignId, LocalDate date, long impressions, long clicks, BigDecimal spend) {
}
