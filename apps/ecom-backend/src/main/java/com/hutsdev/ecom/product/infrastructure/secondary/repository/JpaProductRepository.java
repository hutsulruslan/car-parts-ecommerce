package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.infrastructure.secondary.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByPublicId(UUID publicId);

  int deleteByPublicId(UUID publicId);

  Page<ProductEntity> findAllByFeaturedTrue(Pageable pageable);

  Page<ProductEntity> findBySubCategoryPublicIdAndPublicIdNot(Pageable pageable, UUID subcategoryPublicId, UUID excludedProductPublicId);
}
