package com.example.delivery.web;

import com.example.delivery.api.model.CampaignSummary;
import com.example.delivery.api.model.DeliveryDay;
import com.example.delivery.api.model.DeliveryReport;
import com.example.delivery.domain.Campaign;
import com.example.delivery.domain.CampaignDeliveryReport;
import com.example.delivery.domain.DailyDelivery;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * Maps domain objects to the generated API models.
 */
@Component
public class DeliveryMapper {

    /** Display format expected by the internal reporting dashboard. */
    static final DateTimeFormatter DASHBOARD_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CampaignSummary toSummary(Campaign campaign) {
        return new CampaignSummary()
                .campaignId(campaign.id())
                .name(campaign.name())
                .advertiser(campaign.advertiser());
    }

    public DeliveryReport toReport(CampaignDeliveryReport report) {
        return new DeliveryReport()
                .campaignId(report.campaign().id())
                .campaignName(report.campaign().name())
                .totalImpressions(report.totalImpressions())
                .totalClicks(report.totalClicks())
                .totalSpend(report.totalSpend().doubleValue())
                .days(report.days().stream().map(this::toDay).toList());
    }

    DeliveryDay toDay(DailyDelivery day) {
        return new DeliveryDay()
                .date(day.date().format(DASHBOARD_DATE_FORMAT))
                .impressions(day.impressions())
                .clicks(day.clicks())
                .spend(day.spend().doubleValue());
    }
}
