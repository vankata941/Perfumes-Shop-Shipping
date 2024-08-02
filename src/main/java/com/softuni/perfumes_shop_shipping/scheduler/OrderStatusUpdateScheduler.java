package com.softuni.perfumes_shop_shipping.scheduler;

import com.softuni.perfumes_shop_shipping.service.ShippingDetailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusUpdateScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderStatusUpdateScheduler.class);
    private final ShippingDetailService shippingDetailService;

    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Sofia")
    private void updateOrderStatus() {

        LOGGER.info("Updating Order Statuses...");

        shippingDetailService.updateStatuses();

        LOGGER.info("Finished updating the statuses.");
    }
}
