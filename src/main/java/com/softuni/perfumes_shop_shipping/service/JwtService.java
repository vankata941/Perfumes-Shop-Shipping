package com.softuni.perfumes_shop_shipping.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    UserDetails extractUserInfo(String token);
}
