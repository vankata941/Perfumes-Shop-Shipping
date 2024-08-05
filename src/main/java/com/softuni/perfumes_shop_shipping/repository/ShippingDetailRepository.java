package com.softuni.perfumes_shop_shipping.repository;

import com.softuni.perfumes_shop_shipping.model.entity.ShippingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingDetailRepository extends JpaRepository<ShippingDetail, Long> {

    void deleteByOrderId(Long id);
}
