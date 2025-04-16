package com.hutsdev.ecom.product.application;

import com.hutsdev.ecom.product.domain.aggregate.Brand;
import com.hutsdev.ecom.product.domain.aggregate.Category;
import com.hutsdev.ecom.product.domain.aggregate.Product;
import com.hutsdev.ecom.product.domain.aggregate.SubCategory;
import com.hutsdev.ecom.product.domain.repository.BrandRepository;
import com.hutsdev.ecom.product.domain.repository.CategoryRepository;
import com.hutsdev.ecom.product.domain.repository.ProductRepository;
import com.hutsdev.ecom.product.domain.repository.SubCategoryRepository;
import com.hutsdev.ecom.product.domain.service.BrandCRUD;
import com.hutsdev.ecom.product.domain.service.CategoryCRUD;
import com.hutsdev.ecom.product.domain.service.ProductCRUD;
import com.hutsdev.ecom.product.domain.service.SubCategoryCRUD;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductsApplicationService {

  private ProductCRUD productCRUD;
  private CategoryCRUD categoryCRUD;
  private SubCategoryCRUD subCategoryCRUD;
  private BrandCRUD brandCRUD;

  public ProductsApplicationService(ProductRepository productRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository, BrandRepository brandRepository) {
    this.productCRUD = new ProductCRUD(productRepository);
    this.categoryCRUD = new CategoryCRUD(categoryRepository);
    this.subCategoryCRUD = new SubCategoryCRUD(subCategoryRepository);
    this.brandCRUD = new BrandCRUD(brandRepository);
  }

  @Transactional
  public Product createProduct(Product newProduct) {
    return productCRUD.save(newProduct);
  }

  @Transactional(readOnly = true)
  public Page<Product> findAllProduct(Pageable pageable) {
    return productCRUD.findAll(pageable);
  }

  @Transactional
  public PublicId deleteProduct(PublicId publicId) {
    return productCRUD.delete(publicId);
  }

  @Transactional
  public Category createCategory(Category category) {
    return categoryCRUD.save(category);
  }

  public PublicId deleteCategory(PublicId publicId) {
    return categoryCRUD.delete(publicId);
  }

  @Transactional
  public SubCategory createSubCategory(SubCategory subCategory) {
    return subCategoryCRUD.save(subCategory);
  }

  @Transactional
  public PublicId deleteSubCategory(PublicId publicId) {
    return subCategoryCRUD.delete(publicId);
  }

  @Transactional
  public Brand createBrand(Brand brand) {
    return brandCRUD.save(brand);
  }

  @Transactional
  public PublicId deleteBrand(PublicId publicId) {
    return brandCRUD.delete(publicId);
  }
}
