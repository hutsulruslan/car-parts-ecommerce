package com.hutsdev.ecom.product.domain.vo;

import com.hutsdev.ecom.shared.error.domain.*;

public record ProductDescription(String value) {

  public ProductDescription{
    Assert.field("value", value).notNull().minLength(3);
  }

}
