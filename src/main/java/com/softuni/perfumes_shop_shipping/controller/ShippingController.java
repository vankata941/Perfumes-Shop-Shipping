package com.softuni.perfumes_shop_shipping.controller;

import com.softuni.perfumes_shop_shipping.config.ShippingApiConfig;
import com.softuni.perfumes_shop_shipping.model.dto.inbound.ShippingDetailDTO;
import com.softuni.perfumes_shop_shipping.model.dto.outbound.StatusDTO;
import com.softuni.perfumes_shop_shipping.model.dto.outbound.ViewShippingDetailDTO;
import com.softuni.perfumes_shop_shipping.service.ShippingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingDetailService shippingDetailService;
    private final ShippingApiConfig shippingApiConfig;

    @PostMapping("/create")
    public ResponseEntity<ViewShippingDetailDTO> createShipping(
            @RequestBody ShippingDetailDTO shippingDetailDTO,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        if (userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch("ROLE_ADMIN"::equals)
        ) {
            return ResponseEntity.noContent().build();
        }

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
    public ResponseEntity<List<ViewShippingDetailDTO>> getAllShippingDetails(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch("ROLE_ADMIN"::equals)
        ) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shippingDetailService.getAllDetails());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ViewShippingDetailDTO> deleteShipping(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch("ROLE_ADMIN"::equals)
        ) {
            return ResponseEntity.noContent().build();
        }

        shippingDetailService.deleteShippingByOrderId(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<StatusDTO>> getAllShippingStatuses(@RequestHeader("SHIPPING_KEY") String receivedKey) {

        if (!shippingApiConfig.getKey().equals(receivedKey)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shippingDetailService.getAllStatuses());
    }
}
