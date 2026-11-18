package com.example.delivery.repository;

import com.example.delivery.domain.Campaign;
import com.example.delivery.domain.DailyDelivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    List<Campaign> findAllCampaigns();

    Optional<Campaign> findCampaign(String campaignId);

    List<DailyDelivery> findDeliveryByCampaign(String campaignId);
}
