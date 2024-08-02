package com.softuni.perfumes_shop_shipping.model.dto.inbound;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShippingAddressDTO {

    private Long id;

    private String country;

    private String city;

    private String shippingAddress;

    private String postalCode;
}
