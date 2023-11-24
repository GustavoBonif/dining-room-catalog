package com.diningroom.catalog.feingclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "dining-room-warehouse", path = "/stocks")
public interface WarehouseFeignClients {
    @PostMapping("/createFromProduct")
    ResponseEntity<String> createFromProduct(@RequestParam("productId") Long productId);
}
