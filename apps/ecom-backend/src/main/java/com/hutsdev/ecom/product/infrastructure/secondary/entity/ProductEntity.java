package com.hutsdev.ecom.product.infrastructure.secondary.entity;

import com.hutsdev.ecom.product.domain.aggregate.Product;
import com.hutsdev.ecom.product.domain.aggregate.ProductBuilder;
import com.hutsdev.ecom.product.domain.vo.*;
import com.hutsdev.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Builder
public class ProductEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
  @SequenceGenerator(name = "productSequence", sequenceName = "product_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "description")
  private String description;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private double price;

  @Column(name = "featured")
  private boolean featured;

  @Column(name = "public_id", unique = true)
  private UUID publicId;

  @Column(name = "nb_in_stock")
  private int nbInStock;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
  private Set<PictureEntity> pictures = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "brand_fk", referencedColumnName = "id")
  private BrandEntity brand;

  @ManyToOne
  @JoinColumn(name = "subcategory_fk", referencedColumnName = "id")
  private SubCategoryEntity subCategory;

  public ProductEntity() {
  }

  public ProductEntity(Long id, String description, String name, double price,
                       boolean featured, UUID publicId, int nbInStock,
                       Set<PictureEntity> pictures, BrandEntity brand,
                       SubCategoryEntity subCategory) {
    this.id = id;
    this.description = description;
    this.name = name;
    this.price = price;
    this.featured = featured;
    this.publicId = publicId;
    this.nbInStock = nbInStock;
    this.pictures = pictures;
    this.brand = brand;
    this.subCategory = subCategory;
  }

  public static ProductEntity from(Product product) {
    ProductEntityBuilder productEntityBuilder = ProductEntityBuilder.productEntity();

    if(product.getDbId() != null) {
      productEntityBuilder.id(product.getDbId());
    }

    return productEntityBuilder
      .brand(BrandEntity.from(product.getBrand()))
      .description(product.getDescription().value())
      .name(product.getName().value())
      .price(product.getPrice().value())
      .publicId(product.getPublicId().value())
      .subCategory(SubCategoryEntity.from(product.getSubCategory()))
      .pictures(PictureEntity.from(product.getPictures()))
      .featured(product.getFeature())
      .nbInStock(product.getNbInStock())
      .build();
  }

  public static Product to(ProductEntity productEntity) {
    return ProductBuilder.product()
      .brand(BrandEntity.to(productEntity.getBrand()))
      .description(new ProductDescription(productEntity.getDescription()))
      .name(new ProductName(productEntity.getName()))
      .price(new ProductPrice(productEntity.getPrice()))
      .publicId(new PublicId(productEntity.getPublicId()))
      .subCategory(SubCategoryEntity.to(productEntity.getSubCategory()))
      .pictures(PictureEntity.to(productEntity.getPictures()))
      .featured(productEntity.isFeatured())
      .nbInStock(productEntity.getNbInStock())
      .dbId(productEntity.getId())
      .build();
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public int getNbInStock() {
    return nbInStock;
  }

  public void setNbInStock(int nbInStock) {
    this.nbInStock = nbInStock;
  }

  public Set<PictureEntity> getPictures() {
    return pictures;
  }

  public void setPictures(Set<PictureEntity> pictures) {
    this.pictures = pictures;
  }

  public BrandEntity getBrand() {
    return brand;
  }

  public void setBrand(BrandEntity brand) {
    this.brand = brand;
  }

  public SubCategoryEntity getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(SubCategoryEntity subCategory) {
    this.subCategory = subCategory;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof ProductEntity that)) return false;

    return Double.compare(price, that.price) == 0 && featured == that.featured && nbInStock == that.nbInStock && Objects.equals(description, that.description) && Objects.equals(name, that.name) && Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(description);
    result = 31 * result + Objects.hashCode(name);
    result = 31 * result + Double.hashCode(price);
    result = 31 * result + Boolean.hashCode(featured);
    result = 31 * result + Objects.hashCode(publicId);
    result = 31 * result + nbInStock;
    return result;
  }
}
