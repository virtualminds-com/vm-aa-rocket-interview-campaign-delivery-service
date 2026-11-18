package com.example.delivery.domain;

/**
 * A campaign as stored internally.
 *
 * @param currency ISO-4217 code of the currency the campaign is billed in
 */
public record Campaign(String id, String name, String advertiser, String currency) {
}
