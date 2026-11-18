package com.example.delivery.web;

import com.example.delivery.domain.Campaign;
import com.example.delivery.domain.CampaignDeliveryReport;
import com.example.delivery.domain.DailyDelivery;
import com.example.delivery.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
@Import(DeliveryMapper.class)
class DeliveryControllerTest {

    private static final Campaign CAMPAIGN = new Campaign("cmp-1", "Test Campaign", "Acme", "EUR");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryService deliveryService;

    @Test
    void listsCampaigns() throws Exception {
        given(deliveryService.listCampaigns()).willReturn(List.of(CAMPAIGN));

        mockMvc.perform(get("/campaigns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].campaignId").value("cmp-1"))
                .andExpect(jsonPath("$[0].name").value("Test Campaign"))
                .andExpect(jsonPath("$[0].advertiser").value("Acme"));
    }

    @Test
    void returnsDeliveryReport() throws Exception {
        given(deliveryService.getDeliveryReport("cmp-1")).willReturn(CampaignDeliveryReport.from(CAMPAIGN, List.of(
                new DailyDelivery("cmp-1", LocalDate.of(2026, 10, 26), 1000, 12, new BigDecimal("12.34")))));

        mockMvc.perform(get("/campaigns/cmp-1/delivery"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.campaignId").value("cmp-1"))
                .andExpect(jsonPath("$.totalSpend").value(12.34))
                .andExpect(jsonPath("$.days[0].date").value("26/10/2026"))
                .andExpect(jsonPath("$.days[0].impressions").value(1000));
    }
}
