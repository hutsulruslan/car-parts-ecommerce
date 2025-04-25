package com.hutsdev.ecom.product.infrastructure.secondary.entity;

import com.hutsdev.ecom.product.domain.aggregate.CategoryBuilder;
import com.hutsdev.ecom.product.domain.aggregate.SubCategory;
import com.hutsdev.ecom.product.domain.aggregate.SubCategoryBuilder;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.domain.vo.SubCategoryName;
import com.hutsdev.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product_subcategory")
@Builder
public class SubCategoryEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subCategorySequence")
  @SequenceGenerator(name = "subCategorySequence", sequenceName = "product_subcategory_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "public_id", unique = true, nullable = false)
  private UUID publicId;

  @ManyToOne
  @JoinColumn(name = "category_fk", referencedColumnName = "id", nullable = false)
  private CategoryEntity category;

  @OneToMany(mappedBy = "subCategory")
  private Set<ProductEntity> products;

  public SubCategoryEntity() {
  }

  public SubCategoryEntity(Long id, String name, UUID publicId, CategoryEntity category, Set<ProductEntity> products) {
    this.id = id;
    this.name = name;
    this.publicId = publicId;
    this.category = category;
    this.products = products;
  }

  public static SubCategoryEntity from(SubCategory subCategory) {
    SubCategoryEntityBuilder subCategoryEntityBuilder = SubCategoryEntityBuilder.subCategoryEntity();

    if(subCategory.getDbId() != null) {
      subCategoryEntityBuilder.id(subCategory.getDbId());
    }

    return subCategoryEntityBuilder
      .name(subCategory.getName().value())
      .publicId(subCategory.getPublicId().value())
      .build();
  }

  public static SubCategory to(SubCategoryEntity subCategoryEntity) {
    return SubCategoryBuilder.subCategory()
      .dbId(subCategoryEntity.getId())
      .name(new SubCategoryName(subCategoryEntity.getName()))
      .publicId(new PublicId(subCategoryEntity.getPublicId()))
      .category(CategoryEntity.to(subCategoryEntity.getCategory()))
      .build();
  }

  public Set<ProductEntity> getProducts() {
    return products;
  }

  public void setProducts(Set<ProductEntity> products) {
    this.products = products;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
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

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof SubCategoryEntity that)) return false;

    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}
