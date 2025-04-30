package com.hutsdev.ecom.order.domain.order.aggregate;

import com.hutsdev.ecom.order.domain.order.vo.StripeSessionId;
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

  private String stripeSessionId;

  private PublicId publicId;

  private List<OrderedProduct> orderedProducts;

  public Order(OrderStatus status, User user, String stripeSessionId, PublicId publicId, List<OrderedProduct> orderedProducts) {
    this.status = status;
    this.user = user;
    this.stripeSessionId = stripeSessionId;
    this.publicId = publicId;
    this.orderedProducts = orderedProducts;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public User getUser() {
    return user;
  }

  public String getStripeSessionId() {
    return stripeSessionId;
  }

  public PublicId getPublicId() {
    return publicId;
  }

  public List<OrderedProduct> getOrderedProducts() {
    return orderedProducts;
  }

  public static Order create(User connectedUser, List<OrderedProduct> orderedProducts,
                       StripeSessionId stripeSessionId) {
    return OrderBuilder.order()
      .publicId(new PublicId(UUID.randomUUID()))
      .user(connectedUser)
      .status(OrderStatus.PENDING)
      .orderedProducts(orderedProducts)
      .stripeSessionId(stripeSessionId.value())
      .build();
  }

  public void validatePayment() {
    this.status =  OrderStatus.PAID;
  }
}
