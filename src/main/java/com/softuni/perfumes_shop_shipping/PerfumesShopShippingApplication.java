package com.softuni.perfumes_shop_shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PerfumesShopShippingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerfumesShopShippingApplication.class, args);
	}

}
