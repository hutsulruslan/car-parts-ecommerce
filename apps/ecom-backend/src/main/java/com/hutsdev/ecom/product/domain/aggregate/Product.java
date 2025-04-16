package com.hutsdev.ecom.product.domain.aggreagate;

import com.hutsdev.ecom.product.domain.vo.*;
import com.hutsdev.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class Product {

  private final Brand brand;
  private final ProductDescription description;
  private final ProductName name;
  private final ProductPrice price;
  private final SubCategory subCategory;
  private final List<Picture> pictures;

  private Long dbId;
  private boolean featured;
  private PublicId publicId;
  private int nbInStock;

  public Product(List<Picture> pictures, Brand brand, ProductDescription description,
                 ProductName name, ProductPrice price,
                 SubCategory subCategory, int nbInStock,
                 PublicId publicId, boolean featured, Long dbId) {
    assertMandatoryFields(brand, description, name, price, subCategory, pictures, featured, nbInStock);

    this.pictures = pictures;
    this.brand = brand;
    this.description = description;
    this.name = name;
    this.price = price;
    this.subCategory = subCategory;
    this.nbInStock = nbInStock;
    this.publicId = publicId;
    this.featured = featured;
    this.dbId = dbId;
  }

  private void assertMandatoryFields(Brand brand, ProductDescription description,
                                     ProductName name, ProductPrice price,
                                     SubCategory subCategory,
                                     List<Picture> pictures, boolean featured,
                                     int nbInStock) {
    Assert.notNull("brand", brand);
    Assert.notNull("description", description);
    Assert.notNull("name", name);
    Assert.notNull("price", price);
    Assert.notNull("subCategory", subCategory);
    Assert.notNull("pictures", pictures);
    Assert.notNull("featured", featured);
    Assert.notNull("nbInStock", nbInStock);
  }

  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  public Brand getBrand() {
    return brand;
  }

  public ProductDescription getDescription() {
    return description;
  }

  public ProductName getName() {
    return name;
  }

  public ProductPrice getPrice() {
    return price;
  }

  public SubCategory getSubCategory() {
    return subCategory;
  }

  public List<Picture> getPictures() {
    return pictures;
  }

  public Long getDbId() {
    return dbId;
  }

  public boolean getFeature() {
    return featured;
  }

  public PublicId getPublicId() {
    return publicId;
  }

  public int getNbInStock() {
    return nbInStock;
  }
}
