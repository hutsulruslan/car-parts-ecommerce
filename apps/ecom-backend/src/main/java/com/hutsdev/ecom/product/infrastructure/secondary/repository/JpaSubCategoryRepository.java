package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.infrastructure.secondary.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaSubCategoryRepository extends JpaRepository<SubCategoryEntity, Long> {

  Optional<SubCategoryEntity> findByPublicId(UUID publicId);

  int deleteByPublicId(UUID publicId);

}
