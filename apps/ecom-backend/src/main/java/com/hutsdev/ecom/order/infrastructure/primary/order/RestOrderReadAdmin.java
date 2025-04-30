package com.hutsdev.ecom.order.infrastructure.primary.order;

import com.hutsdev.ecom.order.domain.order.aggregate.Order;
import com.hutsdev.ecom.order.domain.order.vo.OrderStatus;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestOrderReadAdmin(UUID publicId,
                                 OrderStatus status,
                                 List<RestOrderedItemRead> orderedItems,
                                 String address,
                                 String email) {
  public static RestOrderReadAdmin from(Order order) {
    StringBuilder address = new StringBuilder();
    if(order.getUser().getUserAddress() != null) {
      address.append(order.getUser().getUserAddress().street());
      address.append(", ");
      address.append(order.getUser().getUserAddress().city());
      address.append(", ");
      address.append(order.getUser().getUserAddress().zipCode());
    }

    return RestOrderReadAdminBuilder.restOrderReadAdmin()
      .publicId(order.getPublicId().value())
      .status(order.getStatus())
      .orderedItems(RestOrderedItemRead.from(order.getOrderedProducts()))
      .address(address.toString())
      .email(order.getUser().getEmail().value())
      .build();
  }
}
