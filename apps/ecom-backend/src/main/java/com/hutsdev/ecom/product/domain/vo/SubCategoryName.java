package com.hutsdev.ecom.product.domain.vo;

import com.hutsdev.ecom.shared.error.domain.Assert;

public record SubCategoryName(String value) {

  public SubCategoryName{
    Assert.field("value", value).notNull().minLength(3);
  }

}
