package com.hutsdev.ecom.product.domain.repository;

import com.hutsdev.ecom.product.domain.aggregate.FilterQuery;
import com.hutsdev.ecom.product.domain.aggregate.Product;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ProductRepository {

  Product save(Product productToCreate);

  Page<Product> findAll(Pageable pageable);

  int delete(PublicId publicId);

  Page<Product> findAllFeaturedProduct(Pageable pageable);

  Optional<Product> findOne(PublicId publicId);

  Page<Product> findBySubCategoryExcludingOne(Pageable pageable, PublicId subCategoryPublicId, PublicId productPublicId);

  Page<Product> findBySubCategoryAndBrands(Pageable pageable, FilterQuery filterQuery);
}
