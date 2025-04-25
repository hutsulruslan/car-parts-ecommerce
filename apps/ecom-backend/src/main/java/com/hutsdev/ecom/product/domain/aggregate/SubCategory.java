package com.hutsdev.ecom.product.domain.aggregate;

import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.domain.vo.SubCategoryName;
import com.hutsdev.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public class SubCategory {

  private final SubCategoryName name;
  private final Category category;

  private Long dbId;
  private PublicId publicId;

  public SubCategory(SubCategoryName name, Category category,
                     Long dbId, PublicId publicId) {
    assertMandatoryFields(name, category);
    this.name = name;
    this.category = category;
    this.publicId = publicId;
    this.dbId = dbId;
  }

  private void assertMandatoryFields(SubCategoryName name, Category category) {
    Assert.notNull("name", name);
    Assert.notNull("category", category);
  }

  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  public SubCategoryName getName() {
    return name;
  }

  public Category getCategory() {
    return category;
  }

  public Long getDbId() {
    return dbId;
  }

  public PublicId getPublicId() {
    return publicId;
  }
}
