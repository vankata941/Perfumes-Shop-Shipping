package com.softuni.perfumes_shop_shipping.model.dto.inbound;

import com.softuni.perfumes_shop_shipping.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShippingDetailDTO {

    private Long orderId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private OrderStatus status;

    private String orderCreatedDate;

    private ShippingAddressDTO shippingAddress;

}
