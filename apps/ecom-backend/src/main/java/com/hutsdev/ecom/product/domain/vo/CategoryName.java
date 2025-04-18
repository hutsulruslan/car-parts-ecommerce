package com.hutsdev.ecom.product.domain.vo;

import com.hutsdev.ecom.shared.error.domain.Assert;

public record CategoryName(String value) {

  public CategoryName{
    Assert.field("value", value).notNull().minLength(3);
  }

}
