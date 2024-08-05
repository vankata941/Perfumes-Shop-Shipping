package com.softuni.perfumes_shop_shipping.service.impl;

import com.softuni.perfumes_shop_shipping.model.dto.inbound.ShippingDetailDTO;
import com.softuni.perfumes_shop_shipping.model.dto.outbound.StatusDTO;
import com.softuni.perfumes_shop_shipping.model.dto.outbound.ViewShippingDetailDTO;
import com.softuni.perfumes_shop_shipping.model.entity.ShippingAddress;
import com.softuni.perfumes_shop_shipping.model.entity.ShippingDetail;
import com.softuni.perfumes_shop_shipping.model.enums.OrderStatus;
import com.softuni.perfumes_shop_shipping.repository.ShippingDetailRepository;
import com.softuni.perfumes_shop_shipping.service.ShippingAddressService;
import com.softuni.perfumes_shop_shipping.service.ShippingDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingDetailServiceImpl implements ShippingDetailService {

    private final ShippingDetailRepository shippingDetailRepository;
    private final ShippingAddressService shippingAddressService;
    private final ModelMapper modelMapper;

    @Override
    public ViewShippingDetailDTO createShippingDetail(ShippingDetailDTO shippingDetailDTO) {
        ShippingDetail shippingDetail = modelMapper.map(shippingDetailDTO, ShippingDetail.class);
        ShippingAddress shippingAddress = modelMapper.map(shippingDetailDTO.getShippingAddress(), ShippingAddress.class);

        shippingDetail.setShippingAddress(shippingAddress);

        shippingAddressService.saveAddress(shippingAddress);
        shippingDetailRepository.save(shippingDetail);

        return modelMapper.map(shippingDetail, ViewShippingDetailDTO.class);
    }

    @Override
    public void updateStatuses() {
        List<ShippingDetail> allDetails = getShippingDetails();

        for (ShippingDetail shippingDetail : allDetails) {
            if (shippingDetail.getStatus().name().equals("CONFIRMED")) {
                shippingDetail.setStatus(OrderStatus.SHIPPED);
                shippingDetailRepository.save(shippingDetail);
            } else if (shippingDetail.getStatus().name().equals("SHIPPED")) {
                shippingDetail.setStatus(OrderStatus.CONFIRMED);
                shippingDetailRepository.save(shippingDetail);
            }
        }
        if (!allDetails.isEmpty()) {
            ShippingDetail last = allDetails.getLast();
            last.setStatus(OrderStatus.DELIVERED);
            shippingDetailRepository.save(last);
        }
    }

    @Override
    public List<ViewShippingDetailDTO> getAllDetails() {
        List<ShippingDetail> allDetails = getShippingDetails();
        List<ViewShippingDetailDTO> viewDetails = new ArrayList<>();

        allDetails.forEach(detail -> viewDetails
                .add(modelMapper.map(detail, ViewShippingDetailDTO.class)));

        return viewDetails;
    }

    private List<ShippingDetail> getShippingDetails() {
        return shippingDetailRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteShippingByOrderId(Long id) {
        shippingDetailRepository.deleteByOrderId(id);
    }

    @Override
    public List<StatusDTO> getAllStatuses() {
        List<ShippingDetail> shippingDetails = getShippingDetails();

        List<StatusDTO> statuses = new ArrayList<>();
        shippingDetails.forEach(detail -> statuses.add(modelMapper.map(detail, StatusDTO.class)));

        return statuses;
    }
}
