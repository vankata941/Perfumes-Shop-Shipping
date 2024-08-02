package com.softuni.perfumes_shop_shipping.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import com.softuni.perfumes_shop_shipping.model.entity.ShippingAddress;
import com.softuni.perfumes_shop_shipping.model.entity.ShippingDetail;
import com.softuni.perfumes_shop_shipping.model.enums.OrderStatus;
import com.softuni.perfumes_shop_shipping.repository.ShippingAddressRepository;
import com.softuni.perfumes_shop_shipping.repository.ShippingDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ShippingControllerIT {

    @Autowired
    private ShippingDetailRepository shippingDetailRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void createShippingTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/shipping/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                         "orderId": 1,
                                         "firstName": "John",
                                         "lastName": "Doe",
                                         "phoneNumber": "3593593599",
                                         "status": "CONFIRMED",
                                         "orderCreatedDate": "27-07-2024 13:06",
                                         "shippingAddress": {
                                             "country": "testCountry",
                                             "city": "testCity",
                                             "shippingAddress": "testStreet",
                                             "postalCode": "5555"
                                         }
                                     }
                                """)
                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String body = result.getResponse().getContentAsString();

        int id = JsonPath.read(body, "$.id");

        Optional<ShippingDetail> optShippingDetail = shippingDetailRepository.findById((long) id);

        Assertions.assertTrue(optShippingDetail.isPresent());

        ShippingDetail shippingDetail = optShippingDetail.get();

        Assertions.assertEquals("John", shippingDetail.getFirstName());
        Assertions.assertEquals("Doe", shippingDetail.getLastName());
        Assertions.assertEquals("3593593599", shippingDetail.getPhoneNumber());
        Assertions.assertEquals("CONFIRMED", shippingDetail.getStatus().name());
        Assertions.assertEquals("27-07-2024 13:06", shippingDetail.getOrderCreatedDate());

        ShippingAddress shippingAddress = shippingDetail.getShippingAddress();

        Assertions.assertNotNull(shippingAddress);
        Assertions.assertEquals("5555", shippingAddress.getPostalCode());
        Assertions.assertEquals("testCity", shippingAddress.getCity());
        Assertions.assertEquals("testCountry", shippingAddress.getCountry());
        Assertions.assertEquals("testStreet", shippingAddress.getShippingAddress());
    }

    @Test
    public void getAllShippingDetailsTest() throws Exception {
        ShippingDetail shippingDetail = createShippingDetail();

        mockMvc.perform(
                get("/shipping/all")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(shippingDetail.getId().intValue())))
                .andExpect(jsonPath("$[0].orderId", is(shippingDetail.getOrderId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(shippingDetail.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(shippingDetail.getLastName())))
                .andExpect(jsonPath("$[0].phoneNumber", is(shippingDetail.getPhoneNumber())))
                .andExpect(jsonPath("$[0].status", is(shippingDetail.getStatus().name())))
                .andExpect(jsonPath("$[0].orderCreatedDate", is(shippingDetail.getOrderCreatedDate())));
    }

    private ShippingDetail createShippingDetail() {
        ShippingDetail shippingDetail = new ShippingDetail();
        shippingDetail.setOrderId(5L);
        shippingDetail.setFirstName("John");
        shippingDetail.setLastName("Doe");
        shippingDetail.setPhoneNumber("3593593599");
        shippingDetail.setStatus(OrderStatus.CONFIRMED);
        shippingDetail.setOrderCreatedDate("27-07-2024 13:06");
        shippingDetail.setShippingAddress(createShippingAddress());

        shippingDetailRepository.save(shippingDetail);

        return shippingDetail;
    }

    private ShippingAddress createShippingAddress() {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setCountry("testCountry");
        shippingAddress.setCity("testCity");
        shippingAddress.setShippingAddress("testAddress");
        shippingAddress.setPostalCode("5555");

        shippingAddressRepository.save(shippingAddress);

        return shippingAddress;
    }
}
