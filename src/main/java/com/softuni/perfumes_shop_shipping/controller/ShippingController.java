package com.softuni.perfumes_shop_shipping.controller;

import com.softuni.perfumes_shop_shipping.model.dto.inbound.ShippingDetailDTO;
import com.softuni.perfumes_shop_shipping.model.dto.outbound.ViewShippingDetailDTO;
import com.softuni.perfumes_shop_shipping.service.ShippingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingDetailService shippingDetailService;

    @PostMapping("/create")
    public ResponseEntity<ViewShippingDetailDTO> createShipping(
            @RequestBody ShippingDetailDTO shippingDetailDTO) {

        ViewShippingDetailDTO viewShippingDetailDTO =
                shippingDetailService.createShippingDetail(shippingDetailDTO);

        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(viewShippingDetailDTO.getId())
                        .toUri()
        ).body(viewShippingDetailDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ViewShippingDetailDTO>> getAllShippingDetails() {

        return ResponseEntity.ok(shippingDetailService.getAllDetails());
    }

}
