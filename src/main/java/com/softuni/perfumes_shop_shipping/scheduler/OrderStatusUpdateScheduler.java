package com.softuni.perfumes_shop_shipping.scheduler;

import com.softuni.perfumes_shop_shipping.service.ShippingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusUpdateScheduler {

    private final ShippingDetailService shippingDetailService;

    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Sofia")
    private void updateOrderStatus() {
        shippingDetailService.updateStatuses();
    }
}
