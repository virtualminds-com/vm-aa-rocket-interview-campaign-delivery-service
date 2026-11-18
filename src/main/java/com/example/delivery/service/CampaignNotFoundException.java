package com.example.delivery.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CampaignNotFoundException extends RuntimeException {

    public CampaignNotFoundException(String campaignId) {
        super("Campaign not found: " + campaignId);
    }
}
