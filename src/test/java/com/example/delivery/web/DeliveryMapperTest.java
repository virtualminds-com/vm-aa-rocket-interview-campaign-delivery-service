package com.example.delivery.web;

import com.example.delivery.api.model.DeliveryDay;
import com.example.delivery.api.model.DeliveryReport;
import com.example.delivery.domain.Campaign;
import com.example.delivery.domain.CampaignDeliveryReport;
import com.example.delivery.domain.DailyDelivery;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMapperTest {

    private static final Campaign CAMPAIGN = new Campaign("cmp-1", "Test Campaign", "Acme", "EUR");

    private final DeliveryMapper mapper = new DeliveryMapper();

    @Test
    void mapsDayUsingDashboardDateFormat() {
        DailyDelivery day = new DailyDelivery("cmp-1", LocalDate.of(2026, 10, 26), 1000, 12, new BigDecimal("12.34"));

        DeliveryDay mapped = mapper.toDay(day);

        assertThat(mapped.getDate()).isEqualTo("26/10/2026");
        assertThat(mapped.getImpressions()).isEqualTo(1000L);
        assertThat(mapped.getClicks()).isEqualTo(12L);
        assertThat(mapped.getSpend()).isEqualTo(12.34);
    }

    @Test
    void mapsReportTotalsAndKeepsDayOrder() {
        CampaignDeliveryReport report = CampaignDeliveryReport.from(CAMPAIGN, List.of(
                new DailyDelivery("cmp-1", LocalDate.of(2026, 10, 27), 200, 3, new BigDecimal("2.50")),
                new DailyDelivery("cmp-1", LocalDate.of(2026, 10, 26), 100, 1, new BigDecimal("1.25"))));

        DeliveryReport mapped = mapper.toReport(report);

        assertThat(mapped.getCampaignId()).isEqualTo("cmp-1");
        assertThat(mapped.getCampaignName()).isEqualTo("Test Campaign");
        assertThat(mapped.getTotalImpressions()).isEqualTo(300L);
        assertThat(mapped.getTotalClicks()).isEqualTo(4L);
        assertThat(mapped.getTotalSpend()).isEqualTo(3.75);
        assertThat(mapped.getDays()).extracting(DeliveryDay::getDate)
                .containsExactly("26/10/2026", "27/10/2026");
    }
}
