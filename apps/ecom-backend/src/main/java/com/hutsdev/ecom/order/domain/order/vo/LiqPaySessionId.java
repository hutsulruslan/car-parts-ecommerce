package com.hutsdev.ecom.order.domain.order.vo;

import com.hutsdev.ecom.shared.error.domain.Assert;

public record LiqPaySessionId(String value) {
  public LiqPaySessionId {
    Assert.notNull("value", value);
  }
}
