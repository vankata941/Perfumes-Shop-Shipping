package com.softuni.perfumes_shop_shipping.service.impl;

import com.softuni.perfumes_shop_shipping.model.entity.ShippingAddress;
import com.softuni.perfumes_shop_shipping.repository.ShippingAddressRepository;
import com.softuni.perfumes_shop_shipping.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    @Override
    public void saveAddress(ShippingAddress shippingAddress) {
        shippingAddressRepository.save(shippingAddress);
    }
}
