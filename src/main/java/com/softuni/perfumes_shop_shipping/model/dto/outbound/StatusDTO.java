package com.softuni.perfumes_shop_shipping.model.dto.outbound;

import com.softuni.perfumes_shop_shipping.model.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusDTO {

    private Long orderId;

    private OrderStatus status;

}
