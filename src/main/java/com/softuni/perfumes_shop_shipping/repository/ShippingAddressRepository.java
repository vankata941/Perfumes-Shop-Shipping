package com.softuni.perfumes_shop_shipping.repository;

import com.softuni.perfumes_shop_shipping.model.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
