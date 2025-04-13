package com.hutsdev.ecom.order.domain.user.aggregate;

import com.hutsdev.ecom.order.domain.user.vo.AuthorityName;
import com.hutsdev.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public class Authority {

  private AuthorityName name;

  public Authority(AuthorityName name) {
    Assert.notNull("name", name);
    this.name = name;
  }

  public AuthorityName getName() {
    return name;
  }
}
