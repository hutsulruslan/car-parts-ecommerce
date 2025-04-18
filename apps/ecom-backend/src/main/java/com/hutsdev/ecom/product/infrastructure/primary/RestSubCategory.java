package com.hutsdev.ecom.product.infrastructure.primary;

import com.hutsdev.ecom.product.domain.aggregate.CategoryBuilder;
import com.hutsdev.ecom.product.domain.aggregate.SubCategory;
import com.hutsdev.ecom.product.domain.aggregate.SubCategoryBuilder;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.domain.vo.SubCategoryName;
import com.hutsdev.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public record RestSubCategory(UUID publicId, String name, UUID parentCategoryId) {

  public RestSubCategory {
    Assert.notNull("name", name);
    Assert.notNull("parentCategoryId", parentCategoryId);
  }

  public static SubCategory toDomain(RestSubCategory dto) {
    return SubCategoryBuilder.subCategory()
      .name(new SubCategoryName(dto.name()))
      .publicId(dto.publicId() != null ? new PublicId(dto.publicId()) : null)
      .parentCategory(CategoryBuilder.category().publicId(new PublicId(dto.parentCategoryId())).build())
      .build();
  }

  public static RestSubCategory fromDomain(SubCategory domain) {
    return RestSubCategoryBuilder.restSubCategory()
      .name(domain.getName().value())
      .publicId(domain.getPublicId().value())
      .parentCategoryId(domain.getParentCategory().getPublicId().value())
      .build();
  }

}
