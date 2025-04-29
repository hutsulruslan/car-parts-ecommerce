package com.hutsdev.ecom.order.domain.order.aggregate;

import com.hutsdev.ecom.order.domain.order.vo.LiqPaySessionId;
import com.hutsdev.ecom.order.domain.order.vo.OrderStatus;
import com.hutsdev.ecom.order.domain.user.aggregate.User;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class Order {

  private OrderStatus status;

  private User user;

  private String liqPaySessionId;

  private PublicId publicId;

  private List<OrderedProduct> orderedProducts;

  public Order(OrderStatus status, User user, String liqPaySessionId, PublicId publicId, List<OrderedProduct> orderedProducts) {
    this.status = status;
    this.user = user;
    this.liqPaySessionId = liqPaySessionId;
    this.publicId = publicId;
    this.orderedProducts = orderedProducts;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public User getUser() {
    return user;
  }

  public String getLiqPaySessionId() {
    return liqPaySessionId;
  }

  public PublicId getPublicId() {
    return publicId;
  }

  public List<OrderedProduct> getOrderedProducts() {
    return orderedProducts;
  }

  public static Order create(User connectedUser, List<OrderedProduct> orderedProducts,
                       LiqPaySessionId liqPaySessionId) {
    return OrderBuilder.order()
      .publicId(new PublicId(UUID.randomUUID()))
      .user(connectedUser)
      .status(OrderStatus.PENDING)
      .orderedProducts(orderedProducts)
      .liqPaySessionId(liqPaySessionId.value())
      .build();
  }

  public void validatePayment() {
    this.status =  OrderStatus.PAID;
  }
}
