package com.example.delivery.service;

import com.example.delivery.domain.Campaign;
import com.example.delivery.domain.CampaignDeliveryReport;
import com.example.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public List<Campaign> listCampaigns() {
        return repository.findAllCampaigns();
    }

    public CampaignDeliveryReport getDeliveryReport(String campaignId) {
        Campaign campaign = repository.findCampaign(campaignId)
                .orElseThrow(() -> new CampaignNotFoundException(campaignId));
        return CampaignDeliveryReport.from(campaign, repository.findDeliveryByCampaign(campaignId));
    }
}
