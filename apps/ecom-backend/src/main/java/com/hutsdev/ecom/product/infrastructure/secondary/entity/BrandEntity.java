package com.hutsdev.ecom.product.infrastructure.secondary.entity;

import com.hutsdev.ecom.product.domain.aggregate.Brand;
import com.hutsdev.ecom.product.domain.aggregate.BrandBuilder;
import com.hutsdev.ecom.product.domain.aggregate.CategoryBuilder;
import com.hutsdev.ecom.product.domain.vo.ProductBrand;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "brand")
@Builder
public class BrandEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brandSequence")
  @SequenceGenerator(name = "brandSequence", sequenceName = "brand_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "public_id", unique = true, nullable = false)
  private UUID publicId;

  @OneToMany(mappedBy = "brand")
  private Set<ProductEntity> products;

  public BrandEntity() {
  }

  public BrandEntity(Long id, String name,
                     UUID publicId, Set<ProductEntity> products) {
    this.id = id;
    this.products = products;
    this.publicId = publicId;
    this.name = name;
  }

  public static BrandEntity from(Brand brand) {
    BrandEntityBuilder brandEntityBuilder = BrandEntityBuilder.brandEntity();

    if(brand.getDbId() != null) {
      brandEntityBuilder.id(brand.getDbId());
    }

    return brandEntityBuilder
      .name(brand.getName().value())
      .publicId(brand.getPublicId().value())
      .build();
  }

  public static Brand to(BrandEntity brandEntity) {
    return BrandBuilder.brand()
      .name(new ProductBrand(brandEntity.getName()))
      .dbId(brandEntity.getId())
      .publicId(new PublicId(brandEntity.getPublicId()))
      .build();
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public Set<ProductEntity> getProducts() {
    return products;
  }

  public void setProducts(Set<ProductEntity> products) {
    this.products = products;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof BrandEntity that)) return false;

    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}
