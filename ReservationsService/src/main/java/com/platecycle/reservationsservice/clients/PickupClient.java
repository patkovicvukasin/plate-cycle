package com.platecycle.reservationsservice.clients;

import com.platecycle.reservationsservice.dto.DeliveryRequest;
import com.platecycle.reservationsservice.dto.DeliveryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PickupService")
public interface PickupClient {
    @PostMapping("/api/pickup/deliveries")
    DeliveryResponse createDelivery(@RequestBody DeliveryRequest request);
}
