package com.hutsdev.ecom.order.domain.user.vo;

import com.hutsdev.ecom.shared.error.domain.Assert;

public record AuthorityName (String name){

  public AuthorityName {
    Assert.field("name", name).notNull();
  }

}
