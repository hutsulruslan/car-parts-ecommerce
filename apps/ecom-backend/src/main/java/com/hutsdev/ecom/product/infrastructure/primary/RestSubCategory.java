package com.hutsdev.ecom.product.infrastructure.primary;

import com.hutsdev.ecom.product.domain.aggregate.SubCategory;
import com.hutsdev.ecom.product.domain.aggregate.SubCategoryBuilder;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.domain.vo.SubCategoryName;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public class RestSubCategory {

  private UUID publicId;
  private String name;
  private RestCategory category;

  public RestSubCategory(){}

  public RestSubCategory(UUID publicId, String name, RestCategory category) {
    this.publicId = publicId;
    this.name = name;
    this.category = category;
  }

  public static SubCategory toDomain(RestSubCategory dto) {
    return SubCategoryBuilder.subCategory()
      .name(new SubCategoryName(dto.getName()))
      .publicId(dto.getPublicId() != null ? new PublicId(dto.getPublicId()) : null)
      .category(RestCategory.toDomain(dto.getCategory()))
      .build();
  }

  public static RestSubCategory fromDomain(SubCategory subCategory) {
    return RestSubCategoryBuilder.restSubCategory()
      .name(subCategory.getName().value())
      .publicId(subCategory.getPublicId().value())
      .category(RestCategory.fromDomain(subCategory.getCategory()))
      .build();
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RestCategory getCategory() {
    return category;
  }

  public void setCategory(RestCategory category) {
    this.category = category;
  }
}
