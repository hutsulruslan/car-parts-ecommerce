package com.hutsdev.ecom.order.infrastructure.primary.order;

import com.hutsdev.ecom.order.application.OrderApplicationService;
import com.hutsdev.ecom.order.domain.order.aggregate.DetailCartItemRequest;
import com.hutsdev.ecom.order.domain.order.aggregate.DetailCartRequest;
import com.hutsdev.ecom.order.domain.order.aggregate.DetailCartRequestBuilder;
import com.hutsdev.ecom.order.domain.order.aggregate.DetailCartResponse;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

  private final OrderApplicationService orderApplicationService;

  public OrderResource(OrderApplicationService orderApplicationService) {
    this.orderApplicationService = orderApplicationService;
  }

  @GetMapping("/get-cart-details")
  public ResponseEntity<RestDetailCartResponse> getDetails(@RequestParam List<UUID> productIds) {
    List<DetailCartItemRequest> cartItemRequests = productIds.stream()
      .map(uuid -> new DetailCartItemRequest(new PublicId(uuid), 1))
      .toList();

    DetailCartRequest detailCartRequest = DetailCartRequestBuilder.detailCartRequest().items(cartItemRequests).build();
    DetailCartResponse cartDetails = orderApplicationService.getCartDetails(detailCartRequest);
    return ResponseEntity.ok(RestDetailCartResponse.from(cartDetails));
  }

}
