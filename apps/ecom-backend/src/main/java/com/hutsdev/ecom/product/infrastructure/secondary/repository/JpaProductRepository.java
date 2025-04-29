package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.infrastructure.secondary.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByPublicId(UUID publicId);

  int deleteByPublicId(UUID publicId);

  Page<ProductEntity> findAllByFeaturedTrue(Pageable pageable);

  Page<ProductEntity> findBySubCategoryPublicIdAndPublicIdNot(Pageable pageable, UUID subcategoryPublicId, UUID excludedProductPublicId);

  Page<ProductEntity> findAllBySubCategoryPublicId(Pageable pageable, UUID subCategoryId);

  @Query("""
  SELECT p FROM ProductEntity p
  WHERE p.subCategory.publicId = :subCategoryId AND p.brand.publicId IN :brandIds
""")
  Page<ProductEntity> findBySubCategoryAndBrandIn(Pageable pageable, UUID subCategoryId, List<UUID> brandIds);

  List<ProductEntity> findAllByPublicIdIn(List<UUID> publicIds);
}
