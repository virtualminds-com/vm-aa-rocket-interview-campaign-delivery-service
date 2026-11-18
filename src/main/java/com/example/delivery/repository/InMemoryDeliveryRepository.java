package com.example.delivery.repository;

import com.example.delivery.domain.Campaign;
import com.example.delivery.domain.DailyDelivery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Read-only repository backed by a JSON seed file.
 * Stands in for the real reporting store so the service runs without any infrastructure.
 */
@Repository
public class InMemoryDeliveryRepository implements DeliveryRepository {

    private final Map<String, Campaign> campaigns = new LinkedHashMap<>();
    private final List<DailyDelivery> delivery;

    public InMemoryDeliveryRepository(ObjectMapper objectMapper,
                                      @Value("classpath:seed/delivery-seed.json") Resource seed) throws IOException {
        try (InputStream in = seed.getInputStream()) {
            SeedData data = objectMapper.readValue(in, SeedData.class);
            data.campaigns().forEach(c -> campaigns.put(c.id(), c));
            this.delivery = List.copyOf(data.delivery());
        }
    }

    @Override
    public List<Campaign> findAllCampaigns() {
        return List.copyOf(campaigns.values());
    }

    @Override
    public Optional<Campaign> findCampaign(String campaignId) {
        return Optional.ofNullable(campaigns.get(campaignId));
    }

    @Override
    public List<DailyDelivery> findDeliveryByCampaign(String campaignId) {
        return delivery.stream()
                .filter(d -> d.campaignId().equals(campaignId))
                .toList();
    }

    record SeedData(List<Campaign> campaigns, List<DailyDelivery> delivery) {
    }
}
