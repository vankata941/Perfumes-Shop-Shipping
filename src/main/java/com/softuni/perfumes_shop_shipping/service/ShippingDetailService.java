package com.softuni.perfumes_shop_shipping.service;

import com.softuni.perfumes_shop_shipping.model.dto.inbound.ShippingDetailDTO;
import com.softuni.perfumes_shop_shipping.model.dto.outbound.StatusDTO;
import com.softuni.perfumes_shop_shipping.model.dto.outbound.ViewShippingDetailDTO;

import java.util.List;

public interface ShippingDetailService {

    ViewShippingDetailDTO createShippingDetail(ShippingDetailDTO shippingDetailDTO);

    void updateStatuses();

    List<ViewShippingDetailDTO> getAllDetails();

    void deleteShippingByOrderId(Long id);

    List<StatusDTO> getAllStatuses();
}
