package com.hutsdev.ecom.product.infrastructure.primary;

import com.hutsdev.ecom.product.domain.aggregate.Brand;
import com.hutsdev.ecom.product.domain.aggregate.BrandBuilder;
import com.hutsdev.ecom.product.domain.vo.ProductBrand;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public record RestBrand(UUID publicId, String name) {

  public RestBrand {
    Assert.notNull("name", name);
  }

  public static Brand toDomain(RestBrand dto) {
    return BrandBuilder.brand()
      .name(new ProductBrand(dto.name()))
      .publicId(dto.publicId() != null ? new PublicId(dto.publicId()) : null)
      .build();
  }

  public static RestBrand fromDomain(Brand brand) {
    return RestBrandBuilder.restBrand()
      .name(brand.getName().value())
      .publicId(brand.getPublicId().value())
      .build();
  }

}
