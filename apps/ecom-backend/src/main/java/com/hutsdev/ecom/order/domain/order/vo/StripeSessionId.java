package com.hutsdev.ecom.order.domain.order.vo;

import com.hutsdev.ecom.shared.error.domain.Assert;

public record StripeSessionId(String value) {
  public StripeSessionId {
    Assert.notNull("value", value);
  }
}
