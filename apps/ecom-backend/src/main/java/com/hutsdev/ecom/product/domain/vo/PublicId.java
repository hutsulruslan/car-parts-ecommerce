package com.hutsdev.ecom.product.domain.vo;

import com.hutsdev.ecom.shared.error.domain.*;

import java.util.UUID;

public record PublicId(UUID value) {

  public PublicId {
    Assert.notNull("value", value);
  }

}
