package com.hutsdev.ecom.product.infrastructure.primary;

import com.hutsdev.ecom.product.domain.aggregate.Brand;
import com.hutsdev.ecom.product.domain.aggregate.Product;
import com.hutsdev.ecom.product.domain.aggregate.ProductBuilder;
import com.hutsdev.ecom.product.domain.vo.*;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class RestProduct {

  private RestBrand brand;
  private String description;
  private String name;
  private double price;
  private RestSubCategory subCategory;
  private boolean featured;
  private List<RestPicture> pictures;
  private UUID publicId;
  private int nbInStock;

  public RestProduct() {
  }

  public RestProduct(RestBrand brand, String description,
                     String name, double price, RestSubCategory subCategory,
                     boolean featured, List<RestPicture> pictures,
                     UUID publicId, int nbInStock) {
    this.brand = brand;
    this.description = description;
    this.name = name;
    this.price = price;
    this.subCategory = subCategory;
    this.featured = featured;
    this.pictures = pictures;
    this.publicId = publicId;
    this.nbInStock = nbInStock;
  }

  public void addPictureAttachment(List<RestPicture> pictures) {
    this.pictures.addAll(pictures);
  }

  public static Product toDomain(RestProduct restProduct) {
    ProductBuilder productBuilder = ProductBuilder.product()
      .brand(RestBrand.toDomain(restProduct.getBrand()))
      .description(new ProductDescription(restProduct.getDescription()))
      .name(new ProductName(restProduct.getName()))
      .price(new ProductPrice(restProduct.getPrice()))
      .subCategory(RestSubCategory.toDomain(restProduct.getSubCategory()))
      .featured(restProduct.isFeatured())
      .nbInStock(restProduct.getNbInStock());

    if(restProduct.publicId != null) {
      productBuilder.publicId(new PublicId(restProduct.publicId));
    }

    if(restProduct.pictures != null && !restProduct.pictures.isEmpty()) {
      productBuilder.pictures(RestPicture.toDomain(restProduct.getPictures()));
    }

    return productBuilder.build();
  }

  public static RestProduct fromDomain(Product product) {
    return RestProductBuilder.restProduct()
      .brand(RestBrand.fromDomain(product.getBrand()))
      .description(product.getDescription().value())
      .name(product.getName().value())
      .price(product.getPrice().value())
      .featured(product.getFeature())
      .subCategory(RestSubCategory.fromDomain(product.getSubCategory()))
      .pictures(RestPicture.fromDomain(product.getPictures()))
      .publicId(product.getPublicId().value())
      .nbInStock(product.getNbInStock())
      .build();
  }

  public RestBrand getBrand() {
    return brand;
  }

  public void setBrand(RestBrand brand) {
    this.brand = brand;
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

  public RestSubCategory getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(RestSubCategory subCategory) {
    this.subCategory = subCategory;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public List<RestPicture> getPictures() {
    return pictures;
  }

  public void setPictures(List<RestPicture> pictures) {
    this.pictures = pictures;
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
}
