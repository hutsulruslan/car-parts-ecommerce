package com.hutsdev.ecom.product.domain.aggregate;

import com.hutsdev.ecom.product.domain.vo.ProductBrand;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.shared.error.domain.*;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public class Brand {

  private final ProductBrand name;

  private Long dbId;
  private PublicId publicId;

  public Brand(ProductBrand name, Long dbId, PublicId publicId) {
    assertMandatoryFields(name);
    this.name = name;
    this.dbId = dbId;
    this.publicId = publicId;
  }

  private void assertMandatoryFields(ProductBrand name) {
    Assert.notNull("name", name);
  }

  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  public ProductBrand getName() {
    return name;
  }

  public Long getDbId() {
    return dbId;
  }

  public PublicId getPublicId() {
    return publicId;
  }
}
