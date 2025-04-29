package com.hutsdev.ecom.order.domain.order.vo;

import com.hutsdev.ecom.shared.error.domain.Assert;

public record OrderPrice(double value) {
  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
