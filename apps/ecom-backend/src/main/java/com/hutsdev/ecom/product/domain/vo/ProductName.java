package com.hutsdev.ecom.product.domain.vo;

import com.hutsdev.ecom.shared.error.domain.*;

public record ProductName(String value) {

  public ProductName{
    Assert.notNull("value", value);
    Assert.field("value", value).minLength(3).maxLength(256);
  }

}
