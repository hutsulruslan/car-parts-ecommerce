package com.hutsdev.ecom.order.domain.user.aggregate;

import com.hutsdev.ecom.order.domain.user.vo.AuthorityName;
import com.hutsdev.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public class Authority {

  private AuthorityName authorityName;

  public Authority(AuthorityName authorityName) {
    Assert.notNull("name", authorityName);
    this.authorityName = authorityName;
  }

  public AuthorityName getAuthorityName() {
    return authorityName;
  }
}
