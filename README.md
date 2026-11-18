# campaign-delivery-service

Internal service exposing daily delivery statistics (impressions, clicks, spend) per advertising campaign.

## Stack

Java 21, Spring Boot 3.3, Maven. The HTTP API is **contract-first**: `src/main/resources/openapi/delivery-api.yaml`
is the source of truth, and `openapi-generator-maven-plugin` generates the `DeliveryApi` interface and the API models
into `target/generated-sources/openapi` at build time. Generated code is not committed.

Data comes from `src/main/resources/seed/delivery-seed.json`, loaded at startup into an in-memory repository.
No database or Docker is needed.

## Run

```bash
./mvnw spring-boot:run
```

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Example: `curl -s http://localhost:8080/campaigns/cmp-1001/delivery`

## Test

```bash
./mvnw verify
```

In IntelliJ IDEA: after importing, run **Maven → Generate Sources and Update Folders** once so the generated
classes are on the classpath.

## Layout

```
src/main/java/com/example/delivery
├── domain/        Campaign, DailyDelivery, CampaignDeliveryReport (aggregation)
├── repository/    DeliveryRepository + JSON-seeded in-memory implementation
├── service/       DeliveryService, CampaignNotFoundException
└── web/           DeliveryController (implements generated DeliveryApi), DeliveryMapper
src/test/java/com/example/delivery
├── web/           Mapper unit test, @WebMvcTest controller test
└── contract/      Snapshot test of the response the reporting dashboard relies on
```
