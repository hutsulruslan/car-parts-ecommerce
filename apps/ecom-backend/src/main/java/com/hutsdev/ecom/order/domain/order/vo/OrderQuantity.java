package com.hutsdev.ecom.order.domain.order.vo;

import com.hutsdev.ecom.shared.error.domain.Assert;

public record OrderQuantity(long value) {
  public OrderQuantity {
    Assert.field("value", value).positive();
  }
}
