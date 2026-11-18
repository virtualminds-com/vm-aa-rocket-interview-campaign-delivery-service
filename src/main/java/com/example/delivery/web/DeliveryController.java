package com.example.delivery.web;

import com.example.delivery.api.DeliveryApi;
import com.example.delivery.api.model.CampaignSummary;
import com.example.delivery.api.model.DeliveryReport;
import com.example.delivery.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implements the generated {@link DeliveryApi}; request mappings come from the OpenAPI spec.
 */
@RestController
public class DeliveryController implements DeliveryApi {

    private final DeliveryService deliveryService;
    private final DeliveryMapper mapper;

    public DeliveryController(DeliveryService deliveryService, DeliveryMapper mapper) {
        this.deliveryService = deliveryService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<CampaignSummary>> listCampaigns() {
        return ResponseEntity.ok(deliveryService.listCampaigns().stream()
                .map(mapper::toSummary)
                .toList());
    }

    @Override
    public ResponseEntity<DeliveryReport> getCampaignDelivery(String campaignId) {
        return ResponseEntity.ok(mapper.toReport(deliveryService.getDeliveryReport(campaignId)));
    }
}
