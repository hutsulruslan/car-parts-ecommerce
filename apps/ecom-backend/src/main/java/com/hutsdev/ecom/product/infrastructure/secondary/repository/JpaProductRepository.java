package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.infrastructure.secondary.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByPublicId(UUID publicId);

  int deleteByPublicId(UUID publicId);

}
