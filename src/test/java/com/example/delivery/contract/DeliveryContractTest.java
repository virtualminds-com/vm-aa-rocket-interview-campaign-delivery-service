package com.example.delivery.contract;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Guards the response shape the internal reporting dashboard depends on.
 * Runs against the full application with the real seed data.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DeliveryContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deliveryResponseMatchesDashboardContract() throws Exception {
        String expected = new ClassPathResource("contract/delivery-cmp-1001.json").getContentAsString(UTF_8);

        String actual = mockMvc.perform(get("/campaigns/cmp-1001/delivery"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }
}
